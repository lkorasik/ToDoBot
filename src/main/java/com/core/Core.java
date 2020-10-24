package com.core;

import com.authentication.Authenticator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Класс, отвечающий за бизнес-логику бота
 * @author Dmitry
 */
public class Core {
    private HashMap<String, List<Task>> taskContainer = new HashMap<>();
    private Authenticator authenticator = new Authenticator();

    /**
     * Возвращает статус-код идентификации; Предпологается, что если пользователь
     * ввел неправильный пароль, то его просят ввести заново
     * @param userId идентификатор пользователя
     * @param pass пароль пользователя
     * @return false если userId существует, но введен неправильный пароль, иначе true
     */
    public boolean authenticate(String userId, String pass){
        if (authenticator.hasAccount(userId)){
            return authenticator.signIn(userId, pass);
        } else{
            authenticator.signUp(userId, pass);
            return true;
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
        if (tasksFromContainer == null){
            List<Task> newTasks = new ArrayList<>();
            newTasks.add(task);
            taskContainer.put(userId, newTasks);
        }else{
            tasksFromContainer.add(task);
            taskContainer.put(userId, tasksFromContainer);
        }
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
