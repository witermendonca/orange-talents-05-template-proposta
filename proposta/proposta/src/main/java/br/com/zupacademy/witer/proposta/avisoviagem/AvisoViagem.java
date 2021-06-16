package br.com.zupacademy.witer.proposta.avisoviagem;

import br.com.zupacademy.witer.proposta.cartao.Cartao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_aviso_viagem")
public class AvisoViagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime dataAvisoViagem = LocalDateTime.now();

    @NotBlank
    private String destino;

    @NotNull
    @Future
    private LocalDate dataTerminoViagem;

    @NotBlank
    private String ipClient;

    @NotBlank
    private String userAgentClient;

    @NotNull
    @Valid
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public AvisoViagem() {
    }

    public AvisoViagem(@NotBlank String destino,@NotNull @Future LocalDate dataTerminoViagem,@NotBlank String ipClient,
                       @NotBlank String userAgentClient,@NotNull @Valid Cartao cartao) {
        this.destino = destino;
        this.dataTerminoViagem = dataTerminoViagem;
        this.ipClient = ipClient;
        this.userAgentClient = userAgentClient;
        this.cartao = cartao;
    }
}
