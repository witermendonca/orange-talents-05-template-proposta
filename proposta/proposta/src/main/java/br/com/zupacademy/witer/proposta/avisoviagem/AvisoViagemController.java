package br.com.zupacademy.witer.proposta.avisoviagem;

import br.com.zupacademy.witer.proposta.cartao.Cartao;
import br.com.zupacademy.witer.proposta.cartao.CartaoRepository;
import br.com.zupacademy.witer.proposta.servicoexterno.apicartoes.ApiCartaoClient;
import feign.FeignException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class AvisoViagemController {

    private CartaoRepository cartaoRepository;
    private AvisoViagemRepository avisoViagemRepository;
    private ApiCartaoClient apiCartaoClient;

    @Autowired
    public AvisoViagemController(CartaoRepository cartaoRepository, AvisoViagemRepository avisoViagemRepository,
                                 ApiCartaoClient apiCartaoClient) {
        this.cartaoRepository = cartaoRepository;
        this.avisoViagemRepository = avisoViagemRepository;
        this.apiCartaoClient = apiCartaoClient;
    }

    private Logger logger = LogManager.getLogger(AvisoViagemController.class);

    @PostMapping("cartoes/{idCartao}/avisos-viagens")
    @Transactional
    public ResponseEntity<?> criarAvisoViagem(@PathVariable("idCartao") String idCartao,
                                              @RequestBody @Valid AvisoViagemRequest request,
                                              HttpServletRequest httpServletRequest) {

        // validando Cartao existente
        Optional<Cartao> cartao = cartaoRepository.findByIdCartao(idCartao);
        if (cartao.isEmpty()) {
            logger.warn("Cartão não encontrado.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado.");
        }

        // Notificando Sitema legado sobre aviso de viagem.
        try {
            String resultado = apiCartaoClient.notificaAvisoViagem(cartao.get().getIdCartao(),request);
            logger.info("Resultado notificação de aviso de viagem API externa: " + resultado);
        } catch (FeignException e) {
            logger.error("Erro na notificação do aviso de viagem à API externa.");
            throw new ResponseStatusException(HttpStatus.valueOf(e.status()), "Erro no aviso de viagem à API de cartões.");
        }
        // Retorna o User-Agent do Header da solicitação.
        String useAgentClient = httpServletRequest.getHeader("User-Agent");
        // Retorna uma string contendo o endereço IP do cliente que enviou a solicitação
        String ipClint = httpServletRequest.getRemoteAddr();

        AvisoViagem novoAvisoViagem = request.toModel(cartao.get(), ipClint, useAgentClient);
        avisoViagemRepository.save(novoAvisoViagem);
        logger.info("Aviso de viagem criada com sucesso.");

        return ResponseEntity.ok().build();
    }
}