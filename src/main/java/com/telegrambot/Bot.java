/*
    Lev
 */
package com.telegrambot;

import com.core.CommandSplitter;
import com.core.Core;
import com.core.ParsedCommand;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot{
    private final String Token = "1309073462:AAFZKxukxVkrvvVhnHbWUzbnnrvhMRO6k7M";
    private final String BotUserName = "ToDoBot";

    private final Core Core = new Core();

    /**
     * Этот метод вызывается, когда приходит сообщение боту в тг
     * @param update - объект, в ктором есть само сообщение (ну и еще немного полезной инфы)
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            ParsedCommand parsedMessage = CommandSplitter.split(message);

            SendMessage answer = new SendMessage();
            answer.setChatId(update.getMessage().getChatId());

            String command = parsedMessage.getCommand();
            if(command.startsWith("/")){
                if(command.equals("/add")){
                    String result = Core.addTask(parsedMessage.getBody());
                    answer.setText(result);
                }
                else if(command.equals("/del")){
                    String result = Core.deleteTask(parsedMessage.getBody());
                    answer.setText(result);
                }
                else if(command.equals("/show_tasks")){
                    String result = Core.showTasks();
                    answer.setText(result);
                }
                else{
                    answer.setText("This command isn't implemented yet");
                }
            }
            else {
                answer.setText("Your command must starts with /");
            }

            System.out.println(Core.showTasks());

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