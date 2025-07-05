package com.SpringBootApp.UrlShortner.monitoring;

import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MonitoringService {

    private final MeterRegistry meterRegistry;
    
    public void incrementTotalAccountsCreated() {
        meterRegistry.counter("app_url_account_total").increment();
    }

    public void incrementTotalUrlsShortened() {
        meterRegistry.counter("app_url_shortened_total").increment();
    }

    public <T> Mono<T> trackLatency(String name, Mono<T> mono) {
        Timer timer = Timer.builder(name)
            .publishPercentileHistogram()
            .register(meterRegistry);
        return timer.record(() -> mono);
    }

}
