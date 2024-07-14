package com.SpringBootApp.UrlShortner.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name="url")
public class Url {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private int id;
    
    @Column(name="urlHash")
    private String urlHash;

    @Column(name="shortUrl")
    private String shortUrl;
    
    @Column(name="longUrl")
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
