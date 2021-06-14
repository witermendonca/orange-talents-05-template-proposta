package br.com.zupacademy.witer.proposta.config.metrica;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;

@Component
public class MetricasProposta {

	private Counter contadorDePropostasCriadas;
	private Timer timerConsultarProposta;
	private final MeterRegistry meterRegistry;

	public MetricasProposta(MeterRegistry merterRegistry) {
		this.meterRegistry = merterRegistry;
		this.contadorDeMetrica();
	}

	private void contadorDeMetrica() {
		Collection<Tag> tags = new ArrayList<>();
		tags.add(Tag.of("emissora", "Mastercard"));
		tags.add(Tag.of("banco", "Itaú"));

		this.contadorDePropostasCriadas = this.meterRegistry.counter("proposta_criada", tags);
		this.timerConsultarProposta = this.meterRegistry.timer("consultar_proposta_time", tags);
	}

	public void incrementaContadorPropostaCriada() {
		this.contadorDePropostasCriadas.increment();
	}

	public void tempoDeDuracaoCosultaProposta(Duration duracao) {
		timerConsultarProposta.record(duracao);
	}
}
