package br.com.zupacademy.witer.proposta.novaproposta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

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

        propostaRepository.save(novaProposta);

        return ResponseEntity.created(uriComponentsBuilder.path("/propostas/{id}")
                .buildAndExpand(novaProposta.getId()).toUri()).body(novaProposta.getId());

    }
}
//        Resultado Esperado
//        A proposta deve estar armazenada no sistema, com um identificador gerado pelo sistema.
//        Retornar 201 com Header Location preenchido com a URL da nova proposta em caso de sucesso.
//        Retornar 400 quando violado alguma das restrições.