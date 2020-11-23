package com.core;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.BiConsumer;

/**
 * @author Dmitry
 * Класс, используемый для представления задач в логике Core
 */
public class Task {
    private String description;
    private transient Timer timer;

    public Task(String description) {
        this.description = description;
    }

    public void setTimer(Date date, String chatId, BiConsumer<String, String> func){
        timer = new Timer();

        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        func.accept(chatId, Constants.NOTIFICATION_MSG + description);
                    }
        }, date);
    }

    public String getDescription() {
        return description;
    }
}