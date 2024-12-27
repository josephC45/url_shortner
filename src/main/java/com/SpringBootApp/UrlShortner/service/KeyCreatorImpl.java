package com.SpringBootApp.UrlShortner.service;

import java.time.*;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.SpringBootApp.UrlShortner.config.ConfigProp;

import reactor.core.publisher.Mono;

@Service
@EnableConfigurationProperties(ConfigProp.class)
public class KeyCreatorImpl implements KeyCreator {

    private ConfigProp configProp;

    private KeyCreatorImpl(ConfigProp configProp) {
        this.configProp = configProp;
    }

    private long getCurrentDateTimeOffset() {
        return Instant.now().toEpochMilli();
    }

    @Override
    public Mono<String> createKey() {
        return Mono.fromSupplier(() -> {
            StringBuilder resultantHash = new StringBuilder();
            long curTime = getCurrentDateTimeOffset();
            while (curTime > 0) {
                int remainder = (int) (curTime % 62);
                char numToChar;

                if (remainder < 10)
                    numToChar = (char) (remainder + '0');
                else if (remainder < 36)
                    numToChar = (char) ('a' + remainder - 10);
                else
                    numToChar = (char) ('A' + remainder - 36);

                resultantHash.append(numToChar);
                curTime /= configProp.getBaseConversion();
            }
            return resultantHash.reverse().toString();
        });
    }
}
