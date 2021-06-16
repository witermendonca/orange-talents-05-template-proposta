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
import br.com.zupacademy.witer.proposta.cartao.StatusCartao;
import br.com.zupacademy.witer.proposta.servicoexterno.apicartoes.ApiCartaoClient;
import feign.FeignException;

@RestController
public class BloqueioCartaoController {

	private CartaoRepository cartaoRepository;

	private BloqueioCartaoRepository bloqueioCartaoRepository;

	private ApiCartaoClient apiCartaoClient;

	@Autowired
	public BloqueioCartaoController(CartaoRepository cartaoRepository,
			BloqueioCartaoRepository bloqueioCartaoRepository, ApiCartaoClient apiCartaoClient) {
		this.cartaoRepository = cartaoRepository;
		this.bloqueioCartaoRepository = bloqueioCartaoRepository;
		this.apiCartaoClient = apiCartaoClient;
	}

	private Logger logger = LogManager.getLogger(BloqueioCartaoController.class);

	@PostMapping("cartoes/{idCartao}/bloqueios")
	@Transactional
	public ResponseEntity<?> bloquearCartao(@PathVariable("idCartao") String idCartao,
			HttpServletRequest httpServletRequest) {

		// validando Cartao existente
		Optional<Cartao> cartao = cartaoRepository.findByIdCartao(idCartao);
		if (cartao.isEmpty()) {
			logger.warn("Cartão não encontrado.");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado.");
		}

		// validando Cartao desbloqueado
		if (cartao.get().getStatusCartao() == StatusCartao.BLOQUEADO) {
			logger.warn("Cartão já bloqueado.");
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Cartão já bloqueado.");
		}

		// Notificando Sitema legado do Bloqueio
		BloqueioCartaoRequest BloqueioCartaoRequest = new BloqueioCartaoRequest("api-propostas");
		try {
			String resultado = apiCartaoClient.notificaBloqueioCartao(cartao.get().getIdCartao(),
					BloqueioCartaoRequest);
			logger.info("Resultado notificação API cartão: Cartão: " + resultado);
		} catch (FeignException e) {
			logger.error("Erro na notificação do bloqueio do cartão a API externa.");
			throw new ResponseStatusException(HttpStatus.valueOf(e.status()), "Erro no Bloqueio do cartão.");
		}

		// Retorna o User-Agent do Header da solicitação.
		String useAgentClient = httpServletRequest.getHeader("User-Agent");
		// Retorna uma string contendo o endereço IP do cliente que enviou a solicitação
		String ipClint = httpServletRequest.getRemoteAddr();

		BloqueioCartao bloqueiaCartao = new BloqueioCartao(ipClint, useAgentClient, cartao.get(), true);
		bloqueioCartaoRepository.save(bloqueiaCartao);
		cartao.get().bloqueiaCartao(bloqueiaCartao);
		
		logger.info("Bloqueio de cartão realizado com sucesso.");
		return ResponseEntity.ok().build();
	}

}
