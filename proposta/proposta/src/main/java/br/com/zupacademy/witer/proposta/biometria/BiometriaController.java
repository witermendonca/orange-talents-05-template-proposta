package br.com.zupacademy.witer.proposta.biometria;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

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

import br.com.zupacademy.witer.proposta.cartao.Cartao;
import br.com.zupacademy.witer.proposta.cartao.CartaoRepository;

@RestController
public class BiometriaController {

	@Autowired
	private CartaoRepository cartaoRepository;

	@Autowired
	private BiometriaRepository biometriaRepository;

	private Logger logger = LogManager.getLogger(BiometriaController.class);

	@PostMapping("cartoes/{id}/biometrias")
	@Transactional
	public ResponseEntity<?> cadastrarBiometria(@PathVariable("id") String idCartao,
			@RequestBody @Valid NovaBiometriaRequest request, UriComponentsBuilder uriComponentsBuilder) {

		if (request.base64Valido()) {
			Optional<Cartao> cartao = cartaoRepository.findByIdCartao(idCartao);
			if (cartao.isPresent()) {

				Biometria novaBiometria = request.toModel(cartao.get());
				biometriaRepository.save(novaBiometria);
				return ResponseEntity.created(
						uriComponentsBuilder.path("/biometrias/{id}").buildAndExpand(novaBiometria.getId()).toUri())
						.build();
			}

			logger.warn("Cartão não encontrado.");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado.");

		}

		logger.warn("Biometria formato Base64 inválido.");
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Biometria formato Base64 inválido.");
	}

}
