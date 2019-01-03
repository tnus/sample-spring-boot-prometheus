package de.nuss.example.prometheus;

import java.time.ZonedDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class SampleController {

	private Counter metricLogAllCounter = Metrics.counter("sample.log.all.calls", "log", "all");

	/**
	 * This methods logs some statements.
	 * 
	 * @return
	 */
	@GetMapping("log")
	public String logAll() {
		log.trace("trace msg");
		log.debug("debug msg");
		log.info("info msg");
		log.warn("warn msg");
		log.error("error msg");

		metricLogAllCounter.increment();

		return String.format("logs written at %s", ZonedDateTime.now());
	}
}
