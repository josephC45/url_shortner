package com.SpringBootApp.UrlShortner.service;
import java.time.*;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.SpringBootApp.UrlShortner.config.ConfigProp;

@Service
@EnableConfigurationProperties(ConfigProp.class)
public class KeyCreatorImpl implements KeyCreator {

    private ConfigProp configProp;

    public KeyCreatorImpl(ConfigProp configProp){
        this.configProp = configProp;
    }

    public long getCurrentDateTimeOffset(){
        return Instant.now().toEpochMilli();
    }
    
    public String createKey(){
        StringBuilder resultantHash = new StringBuilder();
        long curTime = getCurrentDateTimeOffset();
        while(curTime > 0){
            int temp = (int)(curTime % configProp.getBaseConversion());
            char numToChar = (char)(temp + '0');
            resultantHash.append(numToChar);
            curTime /= configProp.getBaseConversion();
        }
        return resultantHash.toString();
    }
}
