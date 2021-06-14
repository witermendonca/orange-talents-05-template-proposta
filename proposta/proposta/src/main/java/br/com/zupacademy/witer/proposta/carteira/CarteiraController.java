package br.com.zupacademy.witer.proposta.carteira;

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
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class CarteiraController {

    private CartaoRepository cartaoRepository;
    private CarteiraRepository carteiraRepository;
    private ApiCartaoClient apiCartaoClient;

    @Autowired
    public CarteiraController(CartaoRepository cartaoRepository, ApiCartaoClient apiCartaoClient,
                              CarteiraRepository carteiraRepository) {
        this.cartaoRepository = cartaoRepository;
        this.apiCartaoClient = apiCartaoClient;
        this.carteiraRepository = carteiraRepository;
    }

    private Logger logger = LogManager.getLogger(CarteiraController.class);

    @PostMapping("cartoes/{idCartao}/carteiras")
    @Transactional
    public ResponseEntity<?> cadastrarCarteiraCartao(@PathVariable("idCartao") String idCartao,
                                                     @RequestBody @Valid CarteiraRequest carteiraRequest,
                                                     UriComponentsBuilder uriComponentsBuilder){

        // validando Cartao existente.
        Optional<Cartao> cartao = cartaoRepository.findByIdCartao(idCartao);
        if (cartao.isEmpty()) {
            logger.warn("Cartão não encontrado.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado.");
        }
        //Validando Tipo de carteira já existente para cartão.
        if (carteiraRequest.tipoCarteiraExistenteParaCartao(carteiraRepository)){
            logger.warn("Tipo de Carteira já cadastrada para esse cartão");
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Carteira já cadastrada para esse cartão");
        }
        // Associando Carteira ao Sitema legado
        try {
            CarteiraResponse carteiraResponse = apiCartaoClient.associaCarteiraAoCartao(idCartao, carteiraRequest);
            logger.info("Resultado Associação Carteira API externa: {}, idCarteira {}",
                    carteiraResponse.getResultado(), carteiraResponse.getId());

            Carteira carteira = carteiraRequest.toModel(cartao.get(), carteiraResponse.getId());
            carteiraRepository.save(carteira);
            logger.info("Carteira {} criada com sucesso.", carteira.getTipoCarteira());
            return ResponseEntity.created(uriComponentsBuilder.path("/carteiras/{id}").
                    buildAndExpand(carteira.getId()).toUri()).body(carteira.toString());

        } catch (FeignException e) {
            logger.error("Erro na associação da carteira à API externa.");
            throw new ResponseStatusException(HttpStatus.valueOf(e.status()), "Erro na associação da carteira à API de cartões.");
        }
    }
}
