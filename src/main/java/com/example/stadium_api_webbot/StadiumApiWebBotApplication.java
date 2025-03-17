package com.example.stadium_api_webbot;

import com.example.stadium_api_webbot.bot.TelegramBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class StadiumApiWebBotApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(StadiumApiWebBotApplication.class, args);
       /* try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new TelegramBot());
            System.out.println("✅ Bot ishga tushdi!");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }*/

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

            // Spring kontekstidan TelegramBot beanini olish
            TelegramBot telegramBot = context.getBean(TelegramBot.class);
            botsApi.registerBot(telegramBot);

            System.out.println("✅ Bot ishga tushdi!");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
