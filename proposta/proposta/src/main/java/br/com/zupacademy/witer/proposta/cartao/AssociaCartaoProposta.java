package br.com.zupacademy.witer.proposta.cartao;

import br.com.zupacademy.witer.proposta.novaproposta.Proposta;
import br.com.zupacademy.witer.proposta.novaproposta.PropostaRepository;
import br.com.zupacademy.witer.proposta.novaproposta.StatusProposta;
import br.com.zupacademy.witer.proposta.servicoexterno.apicartoes.ApiCartaoClient;
import feign.FeignException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@EnableScheduling
public class AssociaCartaoProposta {

	private PropostaRepository propostaRepository;

	private CartaoRepository cartaoRepository;

	private ApiCartaoClient apiCartaoClient;

	@Autowired
	public AssociaCartaoProposta(PropostaRepository propostaRepository, CartaoRepository cartaoRepository,
			ApiCartaoClient apiCartaoClient) {
		this.propostaRepository = propostaRepository;
		this.cartaoRepository = cartaoRepository;
		this.apiCartaoClient = apiCartaoClient;
	}

	private Logger logger = LogManager.getLogger(AssociaCartaoProposta.class);

	@Scheduled(fixedDelay = 10000)
	@Transactional
	public void associaCartao() {

		List<Proposta> propostasElegiveisSemCartao = propostaRepository
				.findByStatusPropostaAndCartaoIsNull(StatusProposta.ELEGIVEL);

		if (propostasElegiveisSemCartao.size() > 0) {
			logger.info("Foram encontradas propostas elegiveis para associacao");

			// associando as propostas aos cartoes disponiveis até o momento
			propostasElegiveisSemCartao.forEach(proposta -> {
				logger.info("Tentando associar Proposta Id {}", proposta.getId());
				associaCartaoAPropostaElegivel(proposta);
			});

		} else {
			logger.info("Nenhuma proposta elegivel até o momento");
		}
	}

	private void associaCartaoAPropostaElegivel(Proposta proposta) {
		try {
			CartaoResponse cartaoResponse = apiCartaoClient.buscaCartaoDaProposta(proposta.getId());
			Cartao cartao = cartaoResponse.toModel(proposta);
			logger.info("Resposta cartao Id {} titular {}", cartaoResponse.getIdCartao(), cartaoResponse.getTitular());
			cartaoRepository.save(cartao);

			logger.info("Cartao Id {} associado com sucesso a Proposta Id {} status {}", cartaoResponse.getIdCartao(),
					cartaoResponse.getTitular(), proposta.getId(), proposta.getStatusProposta());
		} catch (FeignException e) {
			logger.error("Nenhum Cartao encontrado para Proposta Id {}", proposta.getId());
		}

	}

}
