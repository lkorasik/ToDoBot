package com.core;

import com.fsm.FSM;
import com.fsm.State;

import java.util.ArrayList;
import java.util.List;

public class User {

    private List<Task> toDoTasks;
    private List<Task> completedTasks;
    public FSM fsm;

    public User(){
        toDoTasks = new ArrayList<>();
        completedTasks = new ArrayList<>();
        fsm = new FSM();
    }

    public void eraseAllTasks(){
        completedTasks.clear();
        toDoTasks.clear();
    }

    public State getFsmState() {
        return fsm.getCurrentState();
    }

    public List<Task> getToDoTasks() {
        return toDoTasks;
    }

    public void addTodoTask(Task task){
        this.toDoTasks.add(task);
    }

    public List<Task> getCompletedTasks() {
        return completedTasks;
    }

    public void addCompletedTask(Task task){
        this.completedTasks.add(task);
    }
}
