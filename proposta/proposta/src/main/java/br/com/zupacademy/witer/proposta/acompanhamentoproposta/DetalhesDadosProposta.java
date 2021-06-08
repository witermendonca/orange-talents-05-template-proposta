package br.com.zupacademy.witer.proposta.acompanhamentoproposta;

import br.com.zupacademy.witer.proposta.novaproposta.Proposta;
import br.com.zupacademy.witer.proposta.novaproposta.StatusProposta;
import br.com.zupacademy.witer.proposta.validators.CPFouCNPJ;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class DetalhesDadosProposta {

    @NotBlank
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @CPFouCNPJ
    private String documento;

    @NotBlank
    private String endereco;

    @NotNull
    @Positive
    private BigDecimal salario;

    @NotBlank
    private StatusProposta statusProposta;

    @NotBlank
    private String identificadorCartao;

    public DetalhesDadosProposta(Proposta proposta) {
        this.id = proposta.getId();
        this.nome = proposta.getNome();
        this.email = proposta.getEmail();
        this.documento = proposta.getDocumento();
        this.endereco = proposta.getEndereco();
        this.salario = proposta.getSalario();
        this.statusProposta = proposta.getStatusProposta();
        this.identificadorCartao = proposta.getCartao();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public StatusProposta getStatusProposta() {
        return statusProposta;
    }

    public String getIdentificadorCartao() {
        return identificadorCartao;
    }
}
