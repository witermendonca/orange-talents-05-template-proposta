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

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private ApiCartaoClient apiCartaoClient;

    private Logger logger = LogManager.getLogger(AssociaCartaoProposta.class);

    @Scheduled(fixedDelay = 10000)
    @Transactional
    public void associaCartao () {

        List<Proposta> propostasElegiveisSemCartao = propostaRepository.findByStatusPropostaAndCartaoIsNull(StatusProposta.ELEGIVEL);

        if(propostasElegiveisSemCartao.size() > 0) {
            logger.info("Foram encontradas propostas elegiveis para associacao");

            // associando as propostas aos cartoes disponiveis até o momento
            propostasElegiveisSemCartao.forEach(proposta -> {
                logger.info("Tentando associar Proposta Id={} documento={}", proposta.getId(), proposta.getDocumento());
                associaCartaoAPropostaElegivel(proposta);
            });

        }else {
            logger.info("Nenhuma proposta elegivel até o momento");
        }
    }

    private void associaCartaoAPropostaElegivel(Proposta proposta) {
        try {
            CartaoResponse cartaoResponse = apiCartaoClient.buscaCartaoDaProposta(proposta.getId());

            proposta.setCartao(cartaoResponse.getId());
            propostaRepository.save(proposta);

            logger.info("Cartao id={} titular={} associado com sucesso a Proposta documento={} status={}",
                    cartaoResponse.getId(), cartaoResponse.getTitular() ,proposta.getDocumento(), proposta.getStatusProposta());
        }catch(FeignException e) {
            logger.info("Nenhum Cartao encontrado para Proposta documento={} Id={}", proposta.getDocumento(), proposta.getId());
        }

    }

}
