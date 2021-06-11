package br.com.zupacademy.witer.proposta.cartao;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zupacademy.witer.proposta.cartao.bloqueio.BloqueioCartao;
import br.com.zupacademy.witer.proposta.novaproposta.Proposta;

@Entity
@Table(name = "tb_cartao")
public class Cartao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String idCartao;

	@NotNull
	private LocalDateTime emitidoEm;

	@NotBlank
	private String titular;

	@NotNull
	private Integer limite;

	@NotNull
	@Valid
	@OneToOne(fetch = FetchType.LAZY)
	private Proposta proposta;

	@NotNull
	@Enumerated(EnumType.STRING)
	private StatusCartao statusCartao = StatusCartao.DESBLOQUEADO;

	@Deprecated
	public Cartao() {
	}

	public Cartao(@NotBlank String idCartao, @NotNull LocalDateTime emitidoEm, @NotBlank String titular,
			@NotNull Integer limite, @NotNull @Valid Proposta proposta) {
		super();
		this.idCartao = idCartao;
		this.emitidoEm = emitidoEm;
		this.titular = titular;
		this.limite = limite;
		this.proposta = proposta;
	}

	public String getIdCartao() {
		return idCartao;
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

	public Proposta getProposta() {
		return proposta;
	}

	public StatusCartao getStatusCartao() {
		return statusCartao;
	}

	public void bloqueiaCartao(BloqueioCartao bloqueioCartao) {

		if (bloqueioCartao.getAtivo() == true) {
			this.statusCartao = StatusCartao.BLOQUEADO;
		} else {
			this.statusCartao = StatusCartao.DESBLOQUEADO;
		}

	}

}
