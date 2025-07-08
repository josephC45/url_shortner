package com.personal_project.api_gateway.monitoring;

import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MonitoringService {

    private final MeterRegistry meterRegistry;
    
    public void incrementSuccessfulLogins() {
        meterRegistry.counter("app_login_count", "status", "success").increment();
    }

    public void incrementUnSuccessfulLogins() {  
        meterRegistry.counter("app_login_count", "status", "failure").increment();
    }

    public <T> Mono<T> trackLatency(String name, Mono<T> mono) {
        Timer timer = Timer.builder(name)
            .publishPercentileHistogram()
            .register(meterRegistry);
        return timer.record(() -> mono);
    }

}
