package com.SpringBootApp.UrlShortner.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table("urls")
@NoArgsConstructor
@Getter
@Setter
public class Url {

    @Id
    private int id;
    private String urlHash;
    private String shortUrl;
    private String longUrl;

    public Url(String urlHash, String shortUrl, String longUrl) {
        this.urlHash = urlHash;
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
    }
}
