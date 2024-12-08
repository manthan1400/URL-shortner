package com.url.Service;

import com.url.Entity.Url;
import com.url.Repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    public String shortenUrl(String originalUrl) {
        String shortCode = generateShortCode();
        Url url = new Url();
        url.setShortCode(shortCode);
        url.setOriginalUrl(originalUrl);
        url.setCreatedAt(LocalDateTime.now());
        url.setExpiresAt(LocalDateTime.now().plusDays(30)); // Expires in 30 days
        urlRepository.save(url);
        return shortCode;
    }

    public String getOriginalUrl(String shortCode) {
        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short URL not found or expired"));
        return url.getOriginalUrl();
    }

    private String generateShortCode() {
        return RandomStringUtils.randomAlphanumeric(8); // Generate an 8-character code
    }
}
