package br.com.zupacademy.witer.proposta.novaproposta.consultafinanceira;

public class ConsultaFinanceiraResponse {

    private String nome;

    private String documento;

    private String idProposta;

    private String resultadoSolicitacao;

    public ConsultaFinanceiraResponse(String nome, String documento, String idProposta,
                                        String resultadoSolicitacao) {
        super();
        this.nome = nome;
        this.documento = documento;
        this.idProposta = idProposta;
        this.resultadoSolicitacao = resultadoSolicitacao;
    }

}
