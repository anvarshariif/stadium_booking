package com.example.stadium_api_webbot.service;

import com.example.stadium_api_webbot.entity.User;
import com.example.stadium_api_webbot.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void saveUserContact(long chatId, String phoneNumber) {
        User user = userRepository.findById(chatId).orElseThrow();
        user.setPhone(phoneNumber);
        userRepository.save(user);
    }

    public boolean isHasUser(long chatId) {
        Optional<User> byId = userRepository.findById(chatId);

        return byId.isPresent();
    }
}
