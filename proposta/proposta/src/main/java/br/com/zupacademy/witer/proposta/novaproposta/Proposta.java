package br.com.zupacademy.witer.proposta.novaproposta;

import br.com.zupacademy.witer.proposta.validators.CPFouCNPJ;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_proposta")
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Nome não pode ser vazio ou nulo
    @NotBlank
    private String nome;

    //Email não pode ser vazio, nulo ou inválido
    @NotBlank
    @Email
    private String email;

    //Documento do solicitante deve ser obrigatório e válido
    //O documento necessário deve ser o CPF/CNPJ
    @NotBlank
    @CPFouCNPJ
    private String documento;

    //Endereço não pode ser vazio ou nulo
    @NotBlank
    private String endereco;

    //Salário bruto não pode ser vazio, nulo ou negativo
    @NotNull
    @Positive
    private BigDecimal salario;

    @Enumerated(EnumType.STRING)
    private StatusProposta statusProposta;

    private String cartao;

    @Deprecated
    public Proposta() {
    }

    public Proposta(@NotBlank String nome, @NotBlank @Email String email, @NotBlank String documento,
                    @NotBlank String endereco, @NotNull @Positive BigDecimal salario) {
        this.nome = nome;
        this.email = email;
        this.documento = documento;
        this.endereco = endereco;
        this.salario = salario;

    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }

    public StatusProposta getStatusProposta() {
        return statusProposta;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public String getCartao() { return cartao; }

    public String getEmail() { return email; }

    public String getEndereco() { return endereco; }

    public void setCartao(String cartao) {
        this.cartao = cartao;
    }

    public void setStatusProposta(StatusProposta statusProposta) {
        this.statusProposta = statusProposta;
    }

}
