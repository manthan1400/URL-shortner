package com.url.Controller;

import com.url.Service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/url")
@RequiredArgsConstructor
public class UrlController {

    @Autowired
    private final UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody String originalUrl) {
        String shortCode = urlService.shortenUrl(originalUrl);
        String shortUrl = "http://localhost:8080/api/url/" + shortCode;
        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortCode, HttpServletResponse response) throws IOException, IOException {
        String originalUrl = urlService.getOriginalUrl(shortCode);
        response.sendRedirect(originalUrl);
        return ResponseEntity.status(HttpStatus.FOUND).build();
    }
}
