package com.SpringBootApp.UrlShortner.monitoring;

import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;

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

}
