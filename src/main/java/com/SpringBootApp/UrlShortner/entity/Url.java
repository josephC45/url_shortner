package com.SpringBootApp.UrlShortner.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("url") 
public class Url {

    @Id
    private int id;
    private String urlHash;
    private String shortUrl;
    private String longUrl;

    public Url(){}

    public Url(String urlHash, String shortUrl, String longUrl){
        this.urlHash = urlHash;
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
    }

    public int getId() {
        return id;
    }

    public String getUrlHash() {
        return urlHash;
    }

    public void setUrlHash(String urlHash) {
        this.urlHash = urlHash;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
}
