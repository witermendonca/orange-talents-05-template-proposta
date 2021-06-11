package br.com.zupacademy.witer.proposta.cartao.bloqueio;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BloqueioCartaoRequest {

	@JsonProperty
	private String sistemaResponsavel;

	public BloqueioCartaoRequest(String sistemaResponsavel) {
		this.sistemaResponsavel = sistemaResponsavel;
	}

}
