package com.SpringBootApp.UrlShortner.dto;

public class UrlDto {
    
    private String longUrl;

    public UrlDto(){}

    public UrlDto(String longUrl){
        this.longUrl = longUrl;
    }

    public String getLongUrl(){
        return longUrl;
    }
    public void setLongUrl(String longUrl){
        this.longUrl = longUrl;
    }

}
