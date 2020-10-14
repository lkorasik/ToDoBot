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


    /**
     * Добавляет задание в список задач
     */
    public void addTask(String description) {
        Task task = new Task(description);
        tasks.add(task);
    }

    /**
     * Удаляет задание [задания] из списка задач
     */
    public void deleteTask(String index) throws notExistingTaskIndexException, incorrectTaskIdTypeException {
        try{
            tasks.remove(Integer.parseInt(index));
        } catch (IndexOutOfBoundsException exception){
            throw new notExistingTaskIndexException(index);
        } catch (NumberFormatException exception){
            throw new incorrectTaskIdTypeException();
        }
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