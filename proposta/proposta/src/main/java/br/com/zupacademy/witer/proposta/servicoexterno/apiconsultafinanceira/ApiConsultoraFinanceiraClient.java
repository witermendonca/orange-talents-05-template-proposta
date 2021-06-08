package br.com.zupacademy.witer.proposta.servicoexterno.apiconsultafinanceira;

import br.com.zupacademy.witer.proposta.novaproposta.consultafinanceira.ConsultaFinanceiraRequest;
import br.com.zupacademy.witer.proposta.novaproposta.consultafinanceira.ConsultaFinanceiraResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "${ApiConsultoraFinanceira.host}", name = "ApiConsultoraFinanceira")
public interface ApiConsultoraFinanceiraClient {

    @RequestMapping(method = RequestMethod.POST)
    ConsultaFinanceiraResponse analisa(ConsultaFinanceiraRequest request);
}
