package br.com.zupacademy.witer.proposta.novaproposta;

import br.com.zupacademy.witer.proposta.validators.CPFouCNPJ;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Optional;

public class NovapropostaRequest {

    //Nome não pode ser vazio ou nulo
    @NotBlank
    private String nome;

    //Email não pode ser vazio, nulo ou inválido
    @NotBlank
    @Email
    private String email;

    //Documento do solicitante deve ser obrigatório e válido
    //O documento necessário deve ser o CPF/CNPJ
    @CPFouCNPJ
    @NotBlank
    private String documento;

    //Endereço não pode ser vazio ou nulo
    @NotBlank
    private String endereco;

    //Salário bruto não pode ser vazio, nulo ou negativo
    @NotNull
    @Positive
    private BigDecimal salario;


    public NovapropostaRequest(@NotBlank String nome, @NotBlank @Email String email, @NotBlank String documento,
                               @NotBlank String endereco, @NotNull @Positive BigDecimal salario) {
        this.nome = nome;
        this.email = email;
        this.documento = documento;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Proposta toModel() {
        return new Proposta(nome, email, documento, endereco,salario);
    }

    public boolean propostaExistenteParaDocumento(PropostaRepository repository){

        Optional<Proposta> propostaExistenteDocumento = repository.findByDocumento(documento);

        if (propostaExistenteDocumento.isPresent()){
            return true;
        }
        return false;
    }


}
