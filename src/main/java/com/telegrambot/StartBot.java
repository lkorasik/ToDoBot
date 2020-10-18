package com.telegrambot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author Lev
 *
 * Entry point. Запускаем бота для телеграма
 */
public class StartBot {
    /**
     * Start work with telegram api. Start bot.
     * @param args - args from CMD
     */
    public static void main(String[] args){
        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        Bot bot = new Bot();

        try{
            botsApi.registerBot(bot);
        } catch (TelegramApiException exception){
            exception.printStackTrace();
        }
    }
}
