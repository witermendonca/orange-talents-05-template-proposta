package br.com.zupacademy.witer.proposta.acompanhamentoproposta;

import br.com.zupacademy.witer.proposta.novaproposta.Proposta;
import br.com.zupacademy.witer.proposta.novaproposta.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class DetalhesDadosPropostaController {

    private PropostaRepository propostaRepository;

    @Autowired
    public DetalhesDadosPropostaController(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @GetMapping(value = "/propostas/{id}")
    public ResponseEntity<DetalhesDadosProposta> buscaPropostaPorId(@PathVariable("id") Long id){

        Optional<Proposta>  propostaEncontrada = propostaRepository.findById(id);

        if (propostaEncontrada.isPresent()){
            return ResponseEntity.ok().body(new DetalhesDadosProposta(propostaEncontrada.get()));
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Proposta não encontrada.");

    }
}
