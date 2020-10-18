package com.core;

/**
 * @author Dmitry
 * Класс, используемый для представления задач в логике Core
 */
public class Task {
    private String description;

    public Task(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}