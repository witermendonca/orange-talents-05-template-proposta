package br.com.zupacademy.witer.proposta.cartao.bloqueio;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zupacademy.witer.proposta.cartao.Cartao;

@Entity
@Table(name = "tb_bloqueio_cartao")
public class BloqueioCartao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private LocalDateTime dataBloqueio = LocalDateTime.now();

	@NotBlank
	private String ipClient;

	@NotBlank
	private String useAgentClient;

	@NotNull
	@Valid
	@ManyToOne
	private Cartao cartao;

	@NotNull
	private Boolean ativo = false;

	@Deprecated
	public BloqueioCartao() {
	}

	public BloqueioCartao(@NotBlank String ipClient, @NotBlank String useAgentClient, @NotNull @Valid Cartao cartao,
			@NotNull Boolean ativo) {
		super();
		this.ipClient = ipClient;
		this.useAgentClient = useAgentClient;
		this.cartao = cartao;
		this.ativo = ativo;
	}

	public Boolean getAtivo() {
		return ativo;
	}

}
