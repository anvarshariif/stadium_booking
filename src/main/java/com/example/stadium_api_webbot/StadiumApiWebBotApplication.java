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
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            TelegramBot telegramBot = context.getBean(TelegramBot.class);
            botsApi.registerBot(telegramBot);

            System.out.println("✅ Bot ishga tushdi!");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        /*
        * init datani har safar yuborish shartmi va qachon yuborishni tuzatish
        * auth ishlamayapdi. auth tekshirilmasa ham kirib ketyapdi weappga
        * entitydan dto ga to qilib o'tishni to'g'irlash
        * stadionni rasmlari alohida ishlashini tekshirish
        * admin buyurtmani bekor comment bilan bekor qilsin
        * ownerga report chiqarish kerak
        * */
    }

}
