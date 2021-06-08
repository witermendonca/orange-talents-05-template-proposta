package br.com.zupacademy.witer.proposta.novaproposta.consultafinanceira;

import javax.validation.constraints.NotBlank;

public class ConsultaFinanceiraResponse {

    @NotBlank
    private String nome;

    @NotBlank
    private String documento;

    @NotBlank
    private String idProposta;

    @NotBlank
    private String resultadoSolicitacao;

    public ConsultaFinanceiraResponse(@NotBlank String nome,@NotBlank String documento,@NotBlank String idProposta,
                                      @NotBlank  String resultadoSolicitacao) {
        super();
        this.nome = nome;
        this.documento = documento;
        this.idProposta = idProposta;
        this.resultadoSolicitacao = resultadoSolicitacao;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public String getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }
}
