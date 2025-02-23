package br.com.zupacademy.witer.proposta.servicoexterno.apicartoes;

import br.com.zupacademy.witer.proposta.avisoviagem.AvisoViagemRequest;
import br.com.zupacademy.witer.proposta.cartao.CartaoResponse;
import br.com.zupacademy.witer.proposta.cartao.bloqueio.BloqueioCartaoRequest;

import br.com.zupacademy.witer.proposta.carteira.CarteiraRequest;
import br.com.zupacademy.witer.proposta.carteira.CarteiraResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "${ApiCartao.host}", name = "ApiCartao")
public interface ApiCartaoClient {

	// Query Parameter
	@RequestMapping(method = RequestMethod.GET, path = "?idProposta={id}")
	CartaoResponse buscaCartaoDaProposta(@PathVariable("id") Long id);

	@RequestMapping(method = RequestMethod.POST, path = "/{idCartao}/bloqueios")
	String notificaBloqueioCartao(@PathVariable("idCartao") String idCartao, BloqueioCartaoRequest request);

	@RequestMapping(method = RequestMethod.POST, path = "/{idCartao}/avisos")
	String notificaAvisoViagem(@PathVariable("idCartao") String idCartao, AvisoViagemRequest request);

	@RequestMapping(method = RequestMethod.POST, path = "/{idCartao}/carteiras")
	CarteiraResponse associaCarteiraAoCartao(@PathVariable("idCartao") String idCartao, CarteiraRequest request);

}
