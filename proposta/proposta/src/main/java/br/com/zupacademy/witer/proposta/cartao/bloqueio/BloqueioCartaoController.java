package br.com.zupacademy.witer.proposta.cartao.bloqueio;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.zupacademy.witer.proposta.cartao.Cartao;
import br.com.zupacademy.witer.proposta.cartao.CartaoRepository;

@RestController
public class BloqueioCartaoController {

	@Autowired
	private CartaoRepository cartaoRepository;

	@Autowired
	private BloqueioCartaoRepository bloqueioCartaoRepository;

	private Logger logger = LogManager.getLogger(BloqueioCartaoController.class);

	@PostMapping("cartoes/{idCartao}/bloqueios")
	@Transactional
	public ResponseEntity<?> bloquearCartao(@PathVariable("idCartao") String idCartao,
			HttpServletRequest httpServletRequest) {

		Optional<Cartao> cartao = cartaoRepository.findByIdCartao(idCartao);
		if (cartao.isEmpty()) {
			logger.warn("Cartão não encontrado.");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado.");
		}

		Optional<BloqueioCartao> cartaoBloqueado = bloqueioCartaoRepository.findByCartao(cartao.get());
		if (cartaoBloqueado.isPresent() && cartaoBloqueado.get().getAtivo() == true) {
			logger.warn("Cartão já bloqueado.");
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Cartão já bloqueado.");
		}

		// Retorna o User-Agent do Header da solicitação.
		String useAgentClient = httpServletRequest.getHeader("User-Agent");
		// Retorna uma string contendo o endereço IP do cliente que enviou a solicitação
		String ipClint = httpServletRequest.getRemoteAddr();

		bloqueioCartaoRepository.save(new BloqueioCartao(ipClint, useAgentClient, cartao.get(), true));

		return ResponseEntity.ok().body(useAgentClient);
	}
}
