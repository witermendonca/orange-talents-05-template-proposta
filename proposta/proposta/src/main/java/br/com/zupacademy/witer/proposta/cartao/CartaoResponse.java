package br.com.zupacademy.witer.proposta.cartao;

import br.com.zupacademy.witer.proposta.novaproposta.Proposta;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CartaoResponse {

    @NotBlank
    private String id;

    @NotNull
    private LocalDateTime emitidoEm;

    @NotBlank
    private String titular;

    @NotNull
    private Integer limite;

    @NotBlank
    private String idProposta;

    public CartaoResponse(@NotBlank String id,@NotNull LocalDateTime emitidoEm,
                          @NotBlank String titular,@NotNull Integer limite) {
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
    }

    public String getId() {
        return id;
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

    public String getIdProposta() {
        return idProposta;
    }
}
