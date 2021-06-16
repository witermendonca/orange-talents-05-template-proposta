package br.com.zupacademy.witer.proposta.novaproposta;

import br.com.zupacademy.witer.proposta.config.metrica.MetricasProposta;
import br.com.zupacademy.witer.proposta.novaproposta.consultafinanceira.ConsultaFinanceiraRequest;
import br.com.zupacademy.witer.proposta.novaproposta.consultafinanceira.ConsultaFinanceiraResponse;
import br.com.zupacademy.witer.proposta.servicoexterno.apiconsultafinanceira.ApiConsultoraFinanceiraClient;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class PropostaController {

	private PropostaRepository propostaRepository;

	private ApiConsultoraFinanceiraClient apiConsultoraFinanceiraClient;

	private MetricasProposta metricasProposta;

	@Autowired
	public PropostaController(PropostaRepository propostaRepository,
			ApiConsultoraFinanceiraClient apiConsultoraFinanceiraClient, MetricasProposta metricasProposta) {
		super();
		this.propostaRepository = propostaRepository;
		this.apiConsultoraFinanceiraClient = apiConsultoraFinanceiraClient;
		this.metricasProposta = metricasProposta;
	}

	private final Logger logger = LoggerFactory.getLogger(PropostaController.class);

	@PostMapping("/propostas")
	@Transactional
	public ResponseEntity<?> criarProposta(@RequestBody @Valid NovapropostaRequest resquest,
			UriComponentsBuilder uriComponentsBuilder) {

		// Validando proposta existente para o documento informado.
		if (resquest.propostaExistenteParaDocumento(propostaRepository)) {
			logger.warn("Proposta já existente para o documento informado.");
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
					"Proposta já existente para o documento informado.");
		}

		// Convertendo Proposta request e persistindo novaProposta.
		Proposta novaProposta = resquest.toModel();
		propostaRepository.save(novaProposta);

		StatusProposta statusProposta = null;
		try {
			ConsultaFinanceiraResponse consultaFinanceiraResponse = apiConsultoraFinanceiraClient
					.analisa(new ConsultaFinanceiraRequest(novaProposta));

			logger.info("Proposta Id {} criada com sucesso! Com status {}", consultaFinanceiraResponse.getIdProposta(),
					consultaFinanceiraResponse.getResultadoSolicitacao());
			statusProposta = StatusProposta.ELEGIVEL;

		} catch (FeignException e) {
			if (e.status() == 422) {
				statusProposta = StatusProposta.NAO_ELEGIVEL;
			} else {
				logger.error("Erro na solicitação de analise financeira da proposta.");
				throw new ResponseStatusException(HttpStatus.valueOf(e.status()),
						"Erro na solicitação de analise financeira da proposta.");
			}
		}
		novaProposta.setStatusProposta(statusProposta);

		logger.info("Proposta Id {} criada com sucesso! Com status {}", novaProposta.getId(),
				novaProposta.getStatusProposta());

		metricasProposta.incrementaContadorPropostaCriada(); // incremento a metrica de propostas criada com sucesso

		return ResponseEntity
				.created(uriComponentsBuilder.path("/propostas/{id}").buildAndExpand(novaProposta.getId()).toUri())
				.body(novaProposta.getId());

	}
}
