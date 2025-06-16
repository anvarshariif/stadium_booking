package com.example.stadium_api_webbot.bot;


import com.example.stadium_api_webbot.dto.OrderDTO;
import com.example.stadium_api_webbot.entity.User;
import com.example.stadium_api_webbot.service.AdminService;
import com.example.stadium_api_webbot.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final TelegramBotService telegramBotService;
    private final AdminService adminService;
    private final UserService userService;

    public TelegramBot(TelegramBotService telegramBotService, AdminService adminService, UserService userService) {
        this.telegramBotService = telegramBotService;
        this.adminService = adminService;
        this.userService = userService;
    }
    @Value("${bot.username}")
    private  String botUsername;
    @Value("${bot.token}")
    private  String botToken;
    @Value("${bot.webappUrl}")
    private  String webAppUrl;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

   @Override
   public void onUpdateReceived(Update update) {
       if (update.hasMessage() && update.getMessage().hasText()) {
           String messageText = update.getMessage().getText();
           long chatId = update.getMessage().getChatId();

           User tgUser = telegramBotService.getOrCreatTgUser(update.getMessage());

           if (messageText.equals("/start")) {
               if(tgUser.getPhone()==null){
                   sendRequestContact(chatId);
               }else {
                   sendMainMenu(chatId);
               }
           } else if (messageText.equals("📲 Asosiy menu")) {
               sendWebAppButton(chatId);
           } else if (messageText.equals("🛒 Buyurtmalarim")) {
               sendUserOrders(chatId);
           }
       }else if (update.hasMessage()) {
           Message message = update.getMessage();
           long chatId = message.getChatId();
           if (message.hasContact()) {
               Contact contact = message.getContact();
               String phoneNumber = contact.getPhoneNumber();
               userService.saveUserContact(chatId, phoneNumber);
               sendMainMenu(chatId);
           }
       }
   }




    private void sendRequestContact(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Iltimos, kontakt ma'lumotlaringizni yuboring:");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);

        KeyboardRow row = new KeyboardRow();
        KeyboardButton contactButton = new KeyboardButton("📞 Kontaktni yuborish");
        contactButton.setRequestContact(true); // Kontakt yuborish so‘rovi

        row.add(contactButton);
        keyboardMarkup.setKeyboard(Collections.singletonList(row));

        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendWebAppButton(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("WebApp'ni ochish uchun tugmani bosing:");

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        InlineKeyboardButton webAppButton = new InlineKeyboardButton();
        webAppButton.setText("📲 WebApp'ni ochish");
        webAppButton.setWebApp(new WebAppInfo(webAppUrl));

        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(webAppButton);
        rowsInline.add(rowInline);

        keyboardMarkup.setKeyboard(rowsInline);
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMainMenu(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Menuni tanlang:");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("🛒 Buyurtmalarim"));
        row1.add(new KeyboardButton("📲 Asosiy menu"));

        keyboard.add(row1);

        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendLocation(long chatId, float latitude, float longitude) {
        SendLocation sendLocation = new SendLocation();
        sendLocation.setChatId(chatId);
        sendLocation.setLatitude((double) latitude);
        sendLocation.setLongitude((double) longitude);

        try {
            execute(sendLocation);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendUserOrders(long chatId) {
        List<OrderDTO> orders = adminService.getOrdersForUser(chatId);

        if (orders.isEmpty()) {

            sendMessage(chatId, "Sizda buyurtmalar mavjud emas.");
            return;
        }


        for (OrderDTO order : orders) {
            StringBuilder responseText = new StringBuilder("Sizning buyurtmangiz:\n\n");
            sendLocation(chatId,order.getLatitude(), order.getLongitude());
            responseText.append("====================").append("\n")
                    .append("📍 Stadion: ").append(order.getStadiumName()).append("\n")
                    .append("📍 maydon: ").append(order.getFieldName()).append("\n");
            responseText.append("📅 Sana: ").append(order.getDate()).append("\n");
            responseText.append("⏰ Vaqt: ").append(order.getTime()).append("\n");
            responseText.append("\uD83D\uDCDE buyurtmachi raqami: ").append(order.getPhone()).append("\n");
            responseText.append("📌 Holati: ").append(order.getStatus()).append("\n")
                    .append("====================")
                    .append("\n\n");
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(responseText.toString());
            try {
                execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void sendMessage(long chatId, String text) {
        try {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(text);
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}