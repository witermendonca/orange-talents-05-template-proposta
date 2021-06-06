package br.com.zupacademy.witer.proposta.servicoexterno.apiconsultafinanceira;

import br.com.zupacademy.witer.proposta.novaproposta.consultafinanceira.ConsultaFinanceiraRequest;
import br.com.zupacademy.witer.proposta.novaproposta.consultafinanceira.ConsultaFinanceiraResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "localhost:9999/api/solicitacao", name = "ApiConsultoraFinanceira")
public interface ApiConsultoraFinanceiraClient {

    @PostMapping
    ConsultaFinanceiraResponse analisa(ConsultaFinanceiraRequest request);
}
