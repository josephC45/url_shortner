package com.personal_project.url_feed_service.monitoring;

import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class MonitoringService {

    private final MeterRegistry meterRegistry;
    
    public <T> Flux<T> trackLatency(String name, Flux<T> flux) {
        Timer timer = Timer.builder(name)
            .publishPercentileHistogram()
            .register(meterRegistry);
        return timer.record(() -> flux);
    }

}
