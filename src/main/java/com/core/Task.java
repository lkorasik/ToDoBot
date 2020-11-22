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
    private Timer timer;

    public Task(String description) {
        this.description = description;
    }

    public void setTimer(Date date, String chatId, String text, BiConsumer<String, String> func){
        timer = new Timer();

        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        func.accept(chatId, text);
                    }
        }, date);
    }

    public String getDescription() {
        return description;
    }
}