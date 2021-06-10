package br.com.zupacademy.witer.proposta.biometria;

import java.util.Base64;

import javax.validation.constraints.NotBlank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.zupacademy.witer.proposta.cartao.Cartao;

public class NovaBiometriaRequest {

	@NotBlank
	private String fingerprint;

	public String getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	private Logger logger = LogManager.getLogger(NovaBiometriaRequest.class);

	public boolean base64Valido() {
		Base64.Decoder decoder = Base64.getDecoder();
		try {
			decoder.decode(fingerprint.getBytes());
			logger.info("Formato Base64 válido");
			return true;
		} catch (IllegalArgumentException e) {
			logger.warn("Formato Base64 inválido");
			return false;
		}
	}

	public Biometria toModel(Cartao cartao) {

		return new Biometria(fingerprint, cartao);
	}
}
