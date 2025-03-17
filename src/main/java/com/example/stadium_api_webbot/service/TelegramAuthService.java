package com.example.stadium_api_webbot.service;

import com.example.stadium_api_webbot.entity.User;
import com.example.stadium_api_webbot.repo.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TelegramAuthService {

    @Value("${bot.token}")
    private String botToken;
    private final UserRepository userRepository;

    public TelegramAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean verifyInitData(String initData) {
        try {
            // **1. URL-kodlangan ma'lumotni dekod qilish**
            String decodedInitData = URLDecoder.decode(initData, StandardCharsets.UTF_8);

            // **2. Parametrlarni ajratish**
            Map<String, String> params = parseInitData(decodedInitData);
            if (!params.containsKey("hash")) return false;

            String receivedHash = params.remove("hash");

            // **3. Telegram tartibiga ko'ra parametrlarni yig'ish**
            String dataCheckString = params.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining("\n"));

            // **4. HMAC SHA-256 bilan hash yaratish**
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(getBotTokenHash(), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] finalHashBytes = mac.doFinal(dataCheckString.getBytes(StandardCharsets.UTF_8));

            String calculatedHash = bytesToHex(finalHashBytes);

            return receivedHash.equals(calculatedHash);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private byte[] getBotTokenHash() throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec("WebAppData".getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        return mac.doFinal(botToken.getBytes(StandardCharsets.UTF_8));
    }

    private Map<String, String> parseInitData(String initData) {
        return Arrays.stream(initData.split("&"))
                .map(param -> param.split("=", 2))
                .filter(arr -> arr.length == 2)
                .collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }


    public boolean accessible(String authHeader) {
        if (!authHeader.startsWith("tma ")) {
            return false;
        }
        String initData = authHeader.substring(4);
        return verifyInitData(initData);
    }

    public User getOrCreateUser(String initData) {
        Map<String, String> params = parseInitData(URLDecoder.decode(initData, StandardCharsets.UTF_8));
        String userJson = params.get("user");
        JSONObject userObj = new JSONObject(userJson); // JSON pars qilish
        Long telegramId = userObj.getLong("id");
        System.out.println(telegramId);
        Optional<User> existingUser = userRepository.findByChatId(Long.valueOf(telegramId));
        return existingUser.orElse(null);
    }
}

