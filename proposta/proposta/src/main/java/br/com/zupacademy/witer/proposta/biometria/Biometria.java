package br.com.zupacademy.witer.proposta.biometria;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zupacademy.witer.proposta.cartao.Cartao;

@Entity
@Table(name = "tb_biometria")
public class Biometria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Lob
	private String fingerprint;

	@NotNull
	private LocalDateTime dataCadastro = LocalDateTime.now();

	@NotNull
	@Valid
	@ManyToOne
	private Cartao cartao;

	public Biometria(@NotBlank String fingerprint, @NotNull @Valid Cartao cartao) {
		this.fingerprint = fingerprint;
		this.cartao = cartao;
	}

	public Long getId() {
		return id;
	}

}
