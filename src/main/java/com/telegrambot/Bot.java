/*
    Lev
 */
package com.telegrambot;

import com.core.CommandSplitter;
import com.core.CommandsNames;
import com.core.Core;
import com.core.ParsedCommand;
import org.checkerframework.checker.units.qual.C;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot{
    private final String Token = "1309073462:AAFZKxukxVkrvvVhnHbWUzbnnrvhMRO6k7M";
    private final String BotUserName = "ToDoBot";
    private Core Core = null;

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
            String result;
            if(command.startsWith("/")){
                switch (command){
                    case CommandsNames.start:
                        Core = new Core();
                        result = "I'm ready for work!";
                        break;
                    case CommandsNames.help:
                        result = "/add [text] - You can add task.\n\ttext - task's text\n" +
                                "/del [task_id] - You can delete task.\n\ttask_id - Task's id\n" +
                                "/show - You can see all tasks\n" +
                                "/start - You can start chating with bot\n" +
                                "/help - You will see this message";
                        break;
                    case CommandsNames.addTask:
                        result = Core.addTask(parsedMessage.getBody());
                        break;
                    case CommandsNames.deleteTask:
                        result = Core.deleteTask(parsedMessage.getBody());
                        break;
                    case CommandsNames.showTasks:
                        result = Core.showTasks();
                        break;
                    default:
                        result = "This command isn't implemented yet";
                        break;
                }
            }
            else {
                result = "Your command must starts with /";
            }

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