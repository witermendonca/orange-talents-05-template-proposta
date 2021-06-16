package br.com.zupacademy.witer.proposta.novaproposta.consultafinanceira;

import br.com.zupacademy.witer.proposta.novaproposta.Proposta;
import br.com.zupacademy.witer.proposta.validators.EncryptDecrypt;
import br.com.zupacademy.witer.proposta.validators.IdExistente;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ConsultaFinanceiraRequest {

    @NotBlank
    private String nome;

    @NotBlank
    private String documento;

    @NotNull
    @IdExistente(domainClass = Proposta.class, fieldName = "id")
    private Long idProposta;

    public ConsultaFinanceiraRequest(Proposta proposta) {
        this.nome = proposta.getNome();
        this.documento = EncryptDecrypt.decryption(proposta.getDocumento());
        this.idProposta = proposta.getId();
    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }

    public Long getIdProposta() {
        return idProposta;
    }
}
