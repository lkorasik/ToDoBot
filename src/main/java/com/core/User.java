package com.core;

import com.fsm.FSM;
import com.fsm.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс пользователя. Необходим для сериализации и
 * десериализации пользовательских данных.
 *
 * @author Dmitry
 */
public class User {

    private List<Task> toDoTasks;
    private List<Task> completedTasks;
    private FSM fsm;

    public User() {
        toDoTasks = new ArrayList<>();
        completedTasks = new ArrayList<>();
        fsm = new FSM();
    }

    /**
     * Очищение всех списков задач. Используется для
     * команды /Clear в Core.
     */
    public void eraseAllTasks() {
        completedTasks.clear();
        toDoTasks.clear();
    }

    /**
     * @return Текущее состояние FSM пользователя
     */
    public State getFsmState() {
        return fsm.getCurrentState();
    }

    /**
     * @return Список задач на выполнение
     */
    public List<Task> getToDoTasks() {
        return toDoTasks;
    }

    /**
     * Добавление задачи в список задач на выполнение
     *
     * @param task задача, которую нужно добавить
     */
    public void addTodoTask(Task task) {
        toDoTasks.add(task);
    }

    /**
     * @return Список задач, отмеченных как завершенные
     */
    public List<Task> getCompletedTasks() {
        return completedTasks;
    }

    /**
     * Добавление задачи в список выполненных задач
     *
     * @param task задача, которую нужно добавить
     */
    public void addCompletedTask(Task task) {
        completedTasks.add(task);
    }

    /**
     * Установка состояния FSM пользователя в состояние Listen.
     */
    public void setListenFsmState() {
        fsm.setListenState();
    }

    /**
     * Обновление состояния FSM пользователя по ключю.
     *
     * @param stateKey ключ перехода к новому состоянию
     */
    public void updateFsmState(String stateKey) {
        fsm.updateState(stateKey);
    }
}
