package br.com.zupacademy.witer.proposta.cartao;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zupacademy.witer.proposta.novaproposta.Proposta;

import java.time.LocalDateTime;

public class CartaoResponse {

	@NotBlank
	private String id;

	@NotNull
	private LocalDateTime emitidoEm;

	@NotBlank
	private String titular;

	@NotNull
	private Integer limite;

	@NotBlank
	private String idProposta;

	public CartaoResponse(@NotBlank String id, @NotNull LocalDateTime emitidoEm, @NotBlank String titular,
			@NotNull Integer limite) {
		this.id = id;
		this.emitidoEm = emitidoEm;
		this.titular = titular;
		this.limite = limite;
	}

	public String getIdCartao() {
		return id;
	}

	public LocalDateTime getEmitidoEm() {
		return emitidoEm;
	}

	public String getTitular() {
		return titular;
	}

	public Integer getLimite() {
		return limite;
	}

	public String getIdProposta() {
		return idProposta;
	}

	public Cartao toModel(Proposta proposta) {

		Cartao cartao = new Cartao(id, emitidoEm, titular, limite, proposta);
		proposta.setCartao(cartao);

		return cartao;
	}

}
