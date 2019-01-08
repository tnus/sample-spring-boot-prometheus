package de.nuss.example.prometheus;

import java.time.ZonedDateTime;
import java.util.Random;

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

	@GetMapping(path = "memory", produces = "text/plain")
	public String memory() {
		long freeMemory = Runtime.getRuntime().freeMemory();
		long maxMemory = Runtime.getRuntime().maxMemory();
		long totalMemory = Runtime.getRuntime().totalMemory();

		StringBuilder sb = new StringBuilder();
		sb.append("Memory:\r\n");
		sb.append("---------------------------------\r\n");
		sb.append("free: ").append(freeMemory / 1024 / 1024).append("mb\r\n");
		sb.append("max: ").append(maxMemory / 1024 / 1024).append("mb\r\n");
		sb.append("total: ").append(totalMemory / 1024 / 1024).append("mb\r\n");

		return sb.toString();
	}

	@GetMapping(path = "thread", produces = "text/plain")
	public String startThread() {
		log.info("start thread");
		new Thread(() -> {
			try {
				log.info("thread started");
				Thread.sleep(60_000);
				log.info("thread finished");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();

		return String.format("thread started at %s", ZonedDateTime.now());
	}

	@GetMapping(path = "responseTime", produces = "text/plain")
	public String responseTime() throws InterruptedException {
		long begin = System.currentTimeMillis();

		int sleepTime = new Random().nextInt(300) * 10;
		log.info("sleep {}ms", sleepTime);

		Thread.sleep(sleepTime);

		long end = System.currentTimeMillis();

		return String.format("site generated in %s ms", (end - begin));
	}
}
