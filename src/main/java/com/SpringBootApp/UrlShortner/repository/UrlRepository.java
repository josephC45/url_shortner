package com.SpringBootApp.UrlShortner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SpringBootApp.UrlShortner.entity.Url;

@Repository
public interface UrlRepository extends JpaRepository<Url,Integer> {

    public Optional<Url> findByLongUrl(String longUrl);
    public Optional<Url> findByShortUrl(String shortUrl); 
}
