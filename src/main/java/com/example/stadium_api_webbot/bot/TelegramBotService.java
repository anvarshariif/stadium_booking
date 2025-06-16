package com.example.stadium_api_webbot.bot;

import com.example.stadium_api_webbot.entity.User;
import com.example.stadium_api_webbot.repo.UserRepository;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Service
public class TelegramBotService {
    private final UserRepository userRepository;

    public TelegramBotService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getOrCreatTgUser(Message message) {
        Optional<User> optionalUser = userRepository.findByChatId(message.getChatId());
        if (optionalUser.isEmpty()) {
            String userName = message.getFrom().getUserName();
            Long chatId = message.getChatId();
            User user = User.builder()
                    .chatId(chatId)
                    .fullName(userName)
                    .build();
            userRepository.save(user);
            return user;
        } else {
            return optionalUser.get();
        }
    }
}
