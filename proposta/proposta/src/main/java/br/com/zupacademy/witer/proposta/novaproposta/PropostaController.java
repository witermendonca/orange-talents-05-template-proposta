package br.com.zupacademy.witer.proposta.novaproposta;

import br.com.zupacademy.witer.proposta.novaproposta.consultafinanceira.ConsultaFinanceiraRequest;
import br.com.zupacademy.witer.proposta.servicoexterno.apiconsultafinanceira.ApiConsultoraFinanceiraClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.management.RuntimeErrorException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class PropostaController {

    private PropostaRepository propostaRepository;

    @Autowired
    public PropostaController(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @Autowired
    private ApiConsultoraFinanceiraClient apiConsultoraFinanceiraClient;

    @PostMapping("/propostas")
    @Transactional
    public ResponseEntity<?> criarProposta(@RequestBody @Valid NovapropostaRequest resquest,
                                           UriComponentsBuilder uriComponentsBuilder){

        if (resquest.propostaExistenteParaDocumento(propostaRepository)){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Proposta já existente para esse documento");
        }

        Proposta novaProposta = resquest.toModel();

        StatusProposta statusProposta = null;

        try {
            apiConsultoraFinanceiraClient.analisa(new ConsultaFinanceiraRequest(novaProposta));
            statusProposta = StatusProposta.ELEGIVEL;

        }catch (FeignException e){
            if (e.status() == 422){
                statusProposta = StatusProposta.NAO_ELEGIVEL;
            }else{
                throw new ResponseStatusException(HttpStatus.valueOf(e.status()), e.getMessage());
            }
        }

        novaProposta.setStatusProposta(statusProposta);

        propostaRepository.save(novaProposta);

        return ResponseEntity.created(uriComponentsBuilder.path("/propostas/{id}")
                .buildAndExpand(novaProposta.getId()).toUri()).body(novaProposta.getId());

    }
}
