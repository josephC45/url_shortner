package com.SpringBootApp.UrlShortner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SpringBootApp.UrlShortner.entity.Url;

@Repository
public interface UrlRepository extends JpaRepository<Url,Integer> {

    public Url findByLongUrl(String longUrl);
    public Url findByShortUrl(String shortUrl); 
}
