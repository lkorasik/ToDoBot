/*
    Dmitry
 */
package com.core;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;

public class Core {
    private ArrayList<Task> tasks = new ArrayList<>();

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
            return String.format("Added task: %s", task.description);
        }
    }

    /**
     * Удаляет задание из списка задач
     *
     * @return Successful deletion message
     */
    public String deleteTask(String ids) {
        if (ids == null || StringUtils.isBlank(ids) || ids.equals("")) {
            return "Please enter tasks id";
        }
        String[] strIdsForDelete = ids.split(",");
        Integer[] idsForDelete = new Integer[strIdsForDelete.length];
        for (int i = 0; i < idsForDelete.length; i++) {
            try {
                idsForDelete[i] = Integer.parseInt(strIdsForDelete[i]);
            } catch (NumberFormatException exc) {
                return "Please enter task id as number, not description";
            }
        }
        Integer tasksLenBeforeDel = tasks.size();
        tasks.removeIf(task -> ArrayUtils.contains(idsForDelete, task.getId()));
        if (tasksLenBeforeDel == tasks.size()) {
            return String.format("There is no tasks with id: %s", ids);
        } else {
            return String.format("Successfully deleted tasks with id: %s", ids);
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
                    formattedTasks.append(String.format("%d\t%s", tasks.get(i).getId(), tasks.get(i).description));
                } else {
                    formattedTasks.append(String.format("%d\t%s%n", tasks.get(i).getId(), tasks.get(i).description));
                }
            }
            return formattedTasks.toString();
        }
    }
}