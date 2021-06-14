package br.com.zupacademy.witer.proposta.carteira;

public class CarteiraResponse {

    private String id;

    private  String resultado;

    public CarteiraResponse(String id, String resultado) {
        this.id = id;
        this.resultado = resultado;
    }

    public String getId() {
        return id;
    }

    public String getResultado() {
        return resultado;
    }
}
