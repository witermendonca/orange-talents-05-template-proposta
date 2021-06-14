package br.com.zupacademy.witer.proposta.avisoviagem;

import br.com.zupacademy.witer.proposta.cartao.Cartao;
import br.com.zupacademy.witer.proposta.validators.IdExistente;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoViagemRequest {

    @NotBlank
    private String destino;

    @NotNull
    @Future
    private LocalDate dataTerminoViagem;

   private String ipClient;

   private String userAgentClient;

    public AvisoViagemRequest( @NotBlank String destino, @NotNull @Future LocalDate dataTerminoViagem) {
        this.destino = destino;
        this.dataTerminoViagem = dataTerminoViagem;
    }
    public AvisoViagem toModel(Cartao cartao, String ipClient, String userAgentClient) {
        return new AvisoViagem(destino, dataTerminoViagem, ipClient, userAgentClient, cartao);
    }
}