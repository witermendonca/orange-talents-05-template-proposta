package br.com.zupacademy.witer.proposta.carteira;

import br.com.zupacademy.witer.proposta.cartao.Cartao;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tb_carteira")
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoCarteira tipoCarteira;

    @NotNull
    @Valid
    @ManyToOne
    private Cartao cartao;

    @NotBlank
    private String idCarteira;

    @Deprecated
    public Carteira() {
    }

    public Carteira(@NotBlank @Email String email, @NotNull TipoCarteira tipoCarteira,
                    @NotNull @Valid Cartao cartao, @NotBlank String idCarteira) {
        this.email = email;
        this.tipoCarteira = tipoCarteira;
        this.cartao = cartao;
        this.idCarteira = idCarteira;
    }

    public TipoCarteira getTipoCarteira() {
        return tipoCarteira;
    }

    public Long getId() { return id; }
}
