package com.core;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.apache.commons.io.FileUtils;
import com.fsm.State;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

/**
 * Класс, отвечающий за бизнес-логику бота
 *
 * @author Dmitry
 */
public class Core {
    private final String USERS_FILE;
    private HashMap<String, User> users;

    /**
     * Записывает данные пользователей из JSON файла в `users`
     *
     * @param file_path Абсолютный путь к JSON файлу с пользовательскими данными
     */
    public Core(String file_path) {
        USERS_FILE = file_path;
        var json_file = new File(USERS_FILE);
        var builder = new GsonBuilder();
        var gson = builder.create();
        String jsonString = null;
        try {
            jsonString = FileUtils.readFileToString(
                    json_file, StandardCharsets.UTF_8
            );
        } catch (FileNotFoundException e) {
            users = new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Type type = new TypeToken<HashMap<String, User>>() {
        }.getType();
        HashMap<String, User> json_data = gson.fromJson(jsonString, type);
        if (json_data == null) {
            users = new HashMap<>();
        } else {
            users = json_data;
        }
    }

    public Core() {
        this(Constants.USERS_FILE);
    }

    /**
     * Инициализация пользователя. Должен вызываться вызываться перед добавлением,
     * удалением или любой другой манипуляцией с заданиями пользователя.
     *
     * @param userId Id пользователя
     */
    public void createUser(String userId) {
        var user = new User();
        users.put(userId, user);
        dumpsUserData();
    }

    public State getUserFsmState(String userId) {
        return users.get(userId).getFsmState();
    }

    public State updateUserFsmState(String userId, String stateKey) {
        var user = users.get(userId);
        switch (user.getFsmState()) {
            case SHOW_COMPLETED:
            case SHOW_TODO:
            case HELP:
            case START:
            case CLEAR:
                user.setListenFsmState();
            default:
                user.updateFsmState(stateKey);
        }

        return user.getFsmState();
    }

    /**
     * Записывает текущее состояние поля `users` в JSON файл
     */
    private void dumpsUserData() {
        var gson = new Gson();
        try (FileWriter file = new FileWriter(USERS_FILE)) {
            file.write(gson.toJson(users));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Добавляет задание в список задач конкретного пользователя
     *
     * @param userId      Id пользователя
     * @param description Текст задачи
     */
    public void addTask(String userId, String description) {
        var task = new Task(description);
        users.get(userId).addTodoTask(task);
        dumpsUserData();
    }

    public void setTimer(String uid, String chatId, int taskId, Date date, ISender sender) {
        var user = users.get(uid);
        var tasks = user.getToDoTasks();
        tasks.get(taskId).setTimer(date, chatId, sender);
    }

    /**
     * Удаляет задание из списка задач конкретного пользователя.
     *
     * @param index  Id задачи в списке
     * @param userId Id пользователя у которого нужно удалить задачу
     * @throws IncorrectTaskIdTypeException  при попытке удаления задачи с некорректным Id
     * @throws NotExistingTaskIndexException при попытке удаления несуществующей задачи
     */
    public Task deleteTask(String userId, String index) throws NotExistingTaskIndexException, IncorrectTaskIdTypeException {
        User user = users.get(userId);
        var userTasks = user.getToDoTasks();
        Task completedTask;
        try {
            completedTask = userTasks.remove(Integer.parseInt(index));
        } catch (IndexOutOfBoundsException | NullPointerException exception) {
            throw new NotExistingTaskIndexException(Constants.NOT_EXISTING_TASK_ID_EXCEPTION_MSG + index);
        } catch (NumberFormatException exception) {
            throw new IncorrectTaskIdTypeException(Constants.INCORRECT_TASK_ID_TYPE_EXCEPTION_MSG);
        }
        users.put(userId, user);
        dumpsUserData();
        return completedTask;
    }


    /**
     * Удаление задачи из списка задач и добавление её в список выполненных задач.
     *
     * @param userId Id пользователя
     * @param index  Id задачи из списка задач
     * @throws IncorrectTaskIdTypeException  при попытке удаления задачи с некорректным Id
     * @throws NotExistingTaskIndexException при попытке удаления несуществующей задачи
     */
    public void completeTask(String userId, String index) throws NotExistingTaskIndexException, IncorrectTaskIdTypeException {
        var user = users.get(userId);
        var completedTask = deleteTask(userId, index);
        user.addCompletedTask(completedTask);
        dumpsUserData();
    }

    /**
     * @param userId Id пользователя, у которого нужно показать список задач
     * @return Форматированная строка с списком задач на выполниение
     */
    public String getFormattedToDoTasks(String userId) {
        var user = users.get(userId);
        var tasks = user.getToDoTasks();
        if (tasks.size() == 0) {
            return Constants.EMPTY_TODO_TASK_LIST_MSG;
        } else {
            return buildFormattedString(tasks);
        }
    }

    /**
     * @param userId Id пользователя, у которого нужно показать список задач
     * @return Форматированная строка с списком выполненных задач
     */
    public String getFormattedCompletedTasksString(String userId) {
        var user = users.get(userId);
        var tasks = user.getCompletedTasks();
        if (tasks == null || tasks.size() == 0) {
            return Constants.EMPTY_COMPLETED_TASK_LIST_MSG;
        } else {
            return buildFormattedString(tasks);
        }
    }

    /**
     * @param tasks список, по которому нужно собрать строку
     * @return Форматированная строка, сформированная согласно переданному списку
     */
    private String buildFormattedString(List<Task> tasks) {
        var formattedTasks = new StringBuilder("Id\tОписание\n");
        for (int i = 0; i < tasks.size(); i++) {
            if (i == tasks.size() - 1) {
                formattedTasks.append(String.format("%d\t%s", i, tasks.get(i).getDescription()));
            } else {
                formattedTasks.append(String.format("%d\t%s%n", i, tasks.get(i).getDescription()));
            }
        }
        return formattedTasks.toString();
    }

    /**
     * Очищает список завершенных задач и список задач на выполнение
     *
     * @param userId Id пользователя у которого нужно очистить список задач
     */
    public void clearAllTaskLists(String userId) {
        users.get(userId).eraseAllTasks();
        dumpsUserData();
    }
}