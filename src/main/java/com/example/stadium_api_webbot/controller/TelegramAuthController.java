package com.example.stadium_api_webbot.controller;

import com.example.stadium_api_webbot.entity.User;
import com.example.stadium_api_webbot.service.TelegramAuthService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class TelegramAuthController {
    private final TelegramAuthService authService;

    public TelegramAuthController(TelegramAuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public HttpEntity<?> authenticate(@RequestHeader("Authorization") String authHeader) {
        if (!authService.accessible(authHeader)) {
            return ResponseEntity.status(403).body("forbidden!");
        }
        User user = authService.getOrCreateUser(authHeader.substring(4));
        return ResponseEntity.status(200).body(Map.of("userId", user.getChatId().toString()));
    }
}
