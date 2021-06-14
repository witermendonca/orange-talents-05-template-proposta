package br.com.zupacademy.witer.proposta.acompanhamentoproposta;

import br.com.zupacademy.witer.proposta.config.metrica.MetricasProposta;
import br.com.zupacademy.witer.proposta.novaproposta.Proposta;
import br.com.zupacademy.witer.proposta.novaproposta.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@RestController
public class DetalhesDadosPropostaController {

	private PropostaRepository propostaRepository;

	private MetricasProposta metricasProposta;

	@Autowired
	public DetalhesDadosPropostaController(PropostaRepository propostaRepository, MetricasProposta metricasProposta) {
		this.propostaRepository = propostaRepository;
		this.metricasProposta = metricasProposta;
	}

	@GetMapping(value = "/propostas/{id}")
	public ResponseEntity<DetalhesDadosProposta> buscaPropostaPorId(@PathVariable("id") Long id) {

		Optional<Proposta> propostaEncontrada = propostaRepository.findById(id);
		final Long istanteInicioRequisicao = System.currentTimeMillis();

		if (propostaEncontrada.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Proposta não encontrada.");
		}

		final Long istanteFimRequisicao = System.currentTimeMillis();
		final Long duracaoRequisicaoEmMilisegundos = istanteFimRequisicao - istanteInicioRequisicao;
		metricasProposta.tempoDeDuracaoCosultaProposta(Duration.of(duracaoRequisicaoEmMilisegundos, ChronoUnit.MILLIS));

		return ResponseEntity.ok().body(new DetalhesDadosProposta(propostaEncontrada.get()));

	}
}
