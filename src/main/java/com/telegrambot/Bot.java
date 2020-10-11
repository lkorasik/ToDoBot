/*
    Lev
 */
package com.telegrambot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot{
    private final String Token = "1309073462:AAFZKxukxVkrvvVhnHbWUzbnnrvhMRO6k7M";
    private final String BotUserName = "ToDoBot";

    /**
     * Этот метод вызывается, когда приходит сообщение боту в тг
     * @param update - объект, в ктором есть само сообщение (ну и еще немного полезной инфы)
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            SendMessage message = new SendMessage().setChatId(chat_id).setText(message_text);

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Возвращает имя бота
     * @return Bot's name
     */
    @Override
    public String getBotUsername() {
        return BotUserName;
    }

    /**
     * Возвращает токен доступа к телеграму
     * @return Token
     */
    @Override
    public String getBotToken() {
        return Token;
    }
}