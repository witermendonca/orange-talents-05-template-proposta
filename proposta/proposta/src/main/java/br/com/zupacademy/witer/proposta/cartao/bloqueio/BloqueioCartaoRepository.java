package br.com.zupacademy.witer.proposta.cartao.bloqueio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.zupacademy.witer.proposta.cartao.Cartao;

@Repository
public interface BloqueioCartaoRepository extends JpaRepository<BloqueioCartao, Long> {

	Optional<BloqueioCartao> findByCartao(Cartao cartao);
}
