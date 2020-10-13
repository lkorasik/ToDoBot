/*
    Lev
 */
package com.telegrambot;

import com.core.RequestHandler;
import com.core.CommandsNames;
import com.core.Core;
import com.core.ParsedCommand;
import org.checkerframework.checker.units.qual.C;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot{
    private final String Token;
    private final String BotUserName;
    private final Core Core;

    public Bot(){
        Core = new Core();

        TokenLoader loader = new TokenLoader();

        Token = loader.getToken();
        BotUserName = loader.getName();
    }

    /**
     * Этот метод вызывается, когда приходит сообщение боту в тг
     * @param update - объект, в ктором есть само сообщение (ну и еще немного полезной инфы)
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();

            RequestHandler requestHandler = new RequestHandler();
            ParsedCommand parsedMessage = requestHandler.split(message);

            SendMessage answer = new SendMessage();
            answer.setChatId(update.getMessage().getChatId());

            String command = parsedMessage.getCommand();
            String result = requestHandler.handle(Core, parsedMessage);

            answer.setText(result);

            try{
                execute(answer);
            }
            catch (TelegramApiException exception){
                exception.printStackTrace();
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