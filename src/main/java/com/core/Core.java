package com.core;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry
 * Класс, отвечающий за бизнес-логику бота
 */
public class Core {
    private List<Task> tasks = new ArrayList<>();

    /**
     * Добавляет задание в список задач
     *
     * @param description Текст задачи
     */
    public void addTask(String description) {
        Task task = new Task(description);
        tasks.add(task);
    }

    /**
     * Удаляет задание [задания] из списка задач
     *
     * @param index Id задачи в списке
     */
    public void deleteTask(String index) throws NotExistingTaskIndexException, IncorrectTaskIdTypeException {
        try {
            tasks.remove(Integer.parseInt(index));
        } catch (IndexOutOfBoundsException exception) {
            throw new NotExistingTaskIndexException(Constants.NOT_EXISTING_TASK_ID_EXCEPTION_MSG + index);
        } catch (NumberFormatException exception) {
            throw new IncorrectTaskIdTypeException(Constants.INCORRECT_TASK_ID_TYPE_EXCEPTION_MSG);
        }
    }

    /**
     * Возвращает строку с текущими заданиями
     *
     * @return Formatted string with task's list
     */
    public String getTasks() {
        if (tasks.size() == 0) {
            return Constants.EMPTY_TASK_LIST;
        } else {
            StringBuilder formattedTasks = new StringBuilder("Id\tОписание\n");
            for (int i = 0; i < tasks.size(); i++) {
                if (i == tasks.size() - 1) {
                    formattedTasks.append(String.format("%d\t%s", i, tasks.get(i).getDescription()));
                } else {
                    formattedTasks.append(String.format("%d\t%s%n", i, tasks.get(i).getDescription()));
                }
            }
            return formattedTasks.toString();
        }
    }
}
