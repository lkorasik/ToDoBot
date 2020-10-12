/*
    Dmitry
 */
package com.core;

import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;

/**
 * Класс, отвечающий за бизнес-логику бота
 */
public class Core {
    private ArrayList<Task> tasks = new ArrayList<>();

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Добавляет задание в список задач
     *
     * @return Successful addition message
     */
    public String addTask(String description) {
        if (description == null || StringUtils.isBlank(description) || description.equals("")) {
            return "Please enter not empty task description";
        } else {
            Task task = new Task(description);
            tasks.add(task);
            return String.format("Added task: %s", task.getDescription());
        }
    }

    /**
     * Удаляет задание [задания] из списка задач
     *
     * @return Successful deletion message
     */
    public String deleteTask(String index) {
        if (index == null || StringUtils.isBlank(index) || index.equals("")) {
            return "Please enter tasks id";
        }
        try{
            tasks.remove(Integer.parseInt(index));
        } catch (IndexOutOfBoundsException exception){
            return String.format("There is no task with id: %s", index);
        }
        return String.format("Successfully deleted task with id: %s", index);
    }

    /**
     * Возвращает строку с текущими заданиями
     *
     * @return Formatted string with task's list
     */
    public String showTasks() {
        if (tasks.size() == 0) {
            return "Congratulations! You don't have any tasks yet";
        } else {
            StringBuilder formattedTasks = new StringBuilder("Id\tОписание\n");
            for (int i = 0; i < tasks.size(); i++) {
                if (i == tasks.size() - 1) {
                    formattedTasks.append(String.format("%d\t%s", i, tasks.get(i).getDescription()));
                } else {
                    formattedTasks.append(String.format("%d\t%s%n",i, tasks.get(i).getDescription()));
                }
            }
            return formattedTasks.toString();
        }
    }
}