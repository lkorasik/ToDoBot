package com.core;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.BiConsumer;

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
        File json_file = new File(USERS_FILE);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        try (Scanner fileReader = new Scanner(json_file)){
            StringBuilder jsonString = new StringBuilder();
            while (fileReader.hasNextLine()){
                jsonString.append(fileReader.nextLine());
            }
            Type type = new TypeToken<HashMap<String, User>>(){}.getType();
            HashMap<String, User> json_data = gson.fromJson(jsonString.toString(), type);
            if (json_data == null) {
                users = new HashMap<>();
            } else {
                users = json_data;
            }
        } catch (FileNotFoundException exception) {
            users = new HashMap<>();
        }
    }

    public Core() {this(Constants.USERS_FILE) ; }

    public void createUser(String userId){
        User user = new User();
        users.put(userId, user);
        updateUsersState();
    }

    public State getUserFSMState(String userId) {
        if (users.containsKey(userId)) {
            return users.get(userId).getFsmState();
        }
        else { return null; }
    }

    public void setUserFSMState(String userId, State state){
        users.get(userId).setFsmState(state);
        updateUsersState();
    }

    /**
     * Записывает текущее состояние поля `users` в JSON файл
     */
    private void updateUsersState() {
        Gson gson = new Gson();
        try (FileWriter file = new FileWriter(USERS_FILE)) {
            file.write(gson.toJson(users));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Добавляет задание в список задач конкретного пользователя
     *
     * @param description Текст задачи
     */
    public void addTask(String userId, String description) {
        Task task = new Task(description);
        User user = users.get(userId);
        user.addTodoTask(task);
        updateUsersState();
    }

    public void setTimer(String uid, String chatId, int taskId, Date date, BiConsumer<String, String> func){
        User user = users.get(uid);
        var tasks = user.getToDoTasks();
        tasks.get(taskId).setTimer(date, chatId, func);
    }

    /**
     * Удаляет задание из списка задач конкретного пользователя
     * и в зависимости от параметра markAsCompleted переносит задачу в
     * список завершенных задач
     *
     * @param index Id задачи в списке
     * @param userId Id пользователя у которого нужно удалить задачу
     * @param markAsCompleted true, если нужно удалить задачу из списка для выполнения
     *                        и добавить в список выполненных задач, false если нужно
     *                        просто удалить
     */
    public void deleteTask(String userId, String index, Boolean markAsCompleted) throws NotExistingTaskIndexException, IncorrectTaskIdTypeException {
        try {
            User user = users.get(userId);
            List<Task> userTasks = user.getToDoTasks();
            Task doneTask = userTasks.remove(Integer.parseInt(index));
            if (markAsCompleted){
                List<Task> completedTasks = user.getCompletedTasks();
                if (completedTasks == null){
                    completedTasks = new ArrayList<>();
                    completedTasks.add(doneTask);
                    user.setCompletedTasks(completedTasks);
                }
                else {
                    user.addCompletedTask(doneTask);
                }
            }
            users.put(userId, user);
        } catch (IndexOutOfBoundsException | NullPointerException exception) {
            throw new NotExistingTaskIndexException(Constants.NOT_EXISTING_TASK_ID_EXCEPTION_MSG + index);
        } catch (NumberFormatException exception) {
            throw new IncorrectTaskIdTypeException(Constants.INCORRECT_TASK_ID_TYPE_EXCEPTION_MSG);
        }
        updateUsersState();
    }

    /**
     * @param userId Id пользователя, у которого нужно показать список задач
     * @return Форматированная строка с списком задач на выполниение
     */
    public String getToDoTasks(String userId) {
        User user = users.get(userId);
        if (user == null){
            return Constants.EMPTY_TODO_TASK_LIST_MSG;
        }
        List<Task> tasks = user.getToDoTasks();
        if (tasks == null || tasks.size() == 0) {
            return Constants.EMPTY_TODO_TASK_LIST_MSG;
        } else {
            return buildFormattedString(tasks);
        }
    }

    /**
     * @param userId Id пользователя, у которого нужно показать список задач
     * @return Форматированная строка с списком выполненных задач
     */
    public String getCompletedTasks(String userId) {
        User user = users.get(userId);
        if (user == null){
            return Constants.EMPTY_COMPLETED_TASK_LIST_MSG;
        }
        List<Task> tasks = user.getCompletedTasks();
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
    public String buildFormattedString(List<Task> tasks){
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

    /**
     * Очищает список завершенных задач и список задач на выполнение
     *
     * @param userId Id пользователя у которого нужно очистить список задач
     */
    public void clearTaskList(String userId){
        users.put(userId, new User());
        updateUsersState();
    }
}