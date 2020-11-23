package com.core;

import com.fsm.State;

import java.util.ArrayList;
import java.util.List;

public class User {

    private List<Task> toDoTasks;
    private List<Task> completedTasks;
    private State fsmState;

    public User(){
        toDoTasks = new ArrayList<>();
        completedTasks = new ArrayList<>();
        fsmState = State.ENTRY_POINT;
    }

    public State getFsmState() {
        return fsmState;
    }

    public void setFsmState(State fsmState) {
        this.fsmState = fsmState;
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
}
