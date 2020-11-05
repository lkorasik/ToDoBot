package com.core;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

/**
 * Класс, отвечающий за бизнес-логику бота
 *
 * @author Dmitry
 */
public class Core {
    private final String USERS_FILE;
    private HashMap<String, List<Task>> taskContainer;

    /**
     * Записывает данные пользователей из JSON файла в `taskContainer`
     */
    public Core(String file_path) {
        USERS_FILE = file_path;
        File json_file = new File(USERS_FILE);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        if (json_file.length() == 0) {
            taskContainer = new HashMap<>();
        }
        try(Scanner fileReader = new Scanner(json_file)) {
            while(fileReader.hasNextLine()){
                String jsonString = fileReader.nextLine();
                Type type = new TypeToken<HashMap<String, List<Task>>>() {
                }.getType();
                taskContainer = gson.fromJson(jsonString, type);
            }
        } catch (FileNotFoundException ignored) { ; }
    }

    public Core() {this(Constants.USERS_FILE) ; }

    /**
     * Записывает текущее состояние `TaskContainer` в JSON файл
     */
    private void updateTasksState() {
        Gson gson = new Gson();
        try (FileWriter file = new FileWriter(USERS_FILE)) {
            file.write(gson.toJson(this.taskContainer));

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
        List<Task> tasksFromContainer = taskContainer.get(userId);
        if (tasksFromContainer == null) {
            List<Task> newTasks = new ArrayList<>();
            newTasks.add(task);
            taskContainer.put(userId, newTasks);
        } else {
            tasksFromContainer.add(task);
            taskContainer.put(userId, tasksFromContainer);
        }
        updateTasksState();
    }

    /**
     * Удаляет задание из списка задач конкретного пользователя
     *
     * @param index Id задачи в списке
     */
    public void deleteTask(String userId, String index) throws NotExistingTaskIndexException, IncorrectTaskIdTypeException {
        try {
            List<Task> taskFromContainer = taskContainer.get(userId);
            taskFromContainer.remove(Integer.parseInt(index));
            taskContainer.put(userId, taskFromContainer);
        } catch (IndexOutOfBoundsException | NullPointerException exception) {
            throw new NotExistingTaskIndexException(Constants.NOT_EXISTING_TASK_ID_EXCEPTION_MSG + index);
        } catch (NumberFormatException exception) {
            throw new IncorrectTaskIdTypeException(Constants.INCORRECT_TASK_ID_TYPE_EXCEPTION_MSG);
        }
        updateTasksState();
    }

    /**
     * Возвращает строку с текущими заданиями конкретного пользователя
     *
     * @return Formatted string with task's list
     */
    public String getTasks(String userId) {
        List<Task> tasks = taskContainer.get(userId);
        if (tasks == null || tasks.size() == 0) {
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
