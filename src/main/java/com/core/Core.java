/*
    Dmitry
 */
package com.core;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Iterator;

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
    public String deleteTask(String indexes) {
        if (indexes == null || StringUtils.isBlank(indexes) || indexes.equals("")) {
            return "Please enter tasks id";
        }
        String[] strIdsForDelete = indexes.split(",");
        int[] idsForDelete = new int[strIdsForDelete.length];
        for (int i = 0; i < idsForDelete.length; i++) {
            try {
                idsForDelete[i] = Integer.parseInt(strIdsForDelete[i]);
            } catch (NumberFormatException exc) {
                return "Please enter task id as number, not description";
            }
        }
        int tasksLenBeforeDel = tasks.size();
        Iterator<Task> iter = tasks.iterator();
        while (iter.hasNext()){
            Task task = iter.next();
            if (ArrayUtils.contains(idsForDelete, tasks.indexOf(task))){
                iter.remove();
            }
        }
        if (tasksLenBeforeDel == tasks.size()) {
            return String.format("There is no tasks with id: %s", indexes);
        } else {
            return String.format("Successfully deleted tasks with id: %s", indexes);
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