package br.com.zupacademy.witer.proposta.novaproposta.consultafinanceira;

import br.com.zupacademy.witer.proposta.novaproposta.Proposta;

public class ConsultaFinanceiraRequest {

    private String nome;

    private String documento;

    private Long idProposta;

    public ConsultaFinanceiraRequest(Proposta proposta) {
        this.nome = proposta.getNome();
        this.documento = proposta.getDocumento();
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
