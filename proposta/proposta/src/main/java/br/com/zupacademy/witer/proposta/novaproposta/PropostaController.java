package br.com.zupacademy.witer.proposta.novaproposta;

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

    @PostMapping("/propostas")
    @Transactional
    public ResponseEntity<?> criarProposta(@RequestBody @Valid NovapropostaRequest resquest,
                                           UriComponentsBuilder uriComponentsBuilder){

        Proposta novaProposta = resquest.toModel();

        Optional<Proposta> propostaExistenteDocumento = propostaRepository.findByDocumento(resquest.getDocumento());

        if (propostaExistenteDocumento.isPresent()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Proposta já existente para esse documento");
        }
        propostaRepository.save(novaProposta);

        return ResponseEntity.created(uriComponentsBuilder.path("/propostas/{id}")
                .buildAndExpand(novaProposta.getId()).toUri()).body(novaProposta.getId());

    }
}
