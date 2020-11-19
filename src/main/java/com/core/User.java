package com.core;

import com.fsm.State;

import java.util.List;

public class User {

    private String id;
    private List<Task> toDoTasks;
    private List<Task> completedTasks;
    private State fsmState;

    public User(String userId){
        id = userId;
    }

    public State getFsmState() {
        return fsmState;
    }

    public void setFsmState(State fsmState) {
        this.fsmState = fsmState;
    }

    public String getId() {
        return id;
    }

    public List<Task> getToDoTasks() {
        return toDoTasks;
    }

    public void setToDoTasks(List<Task> toDoTasks) {
        this.toDoTasks = toDoTasks;
    }

    public void addTodoTask(Task task){
        this.toDoTasks.add(task);
    }

    public List<Task> getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(List<Task> completedTasks) {
        this.completedTasks = completedTasks;
    }

    public void addCompletedTask(Task task){
        this.completedTasks.add(task);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                '}';
    }
}
