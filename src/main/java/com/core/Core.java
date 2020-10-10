package com.core;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Core {
    private final List<String> COMMANDS = Arrays.asList("/add", "/show_tasks", "/del");
    private ArrayList<Task> tasks = new ArrayList<>();
    private String request;
    public String response;

    public void sendRequest(String request){
        this.request = request;
        this.handleRequest();
    }

    private void handleRequest(){
        String command = "";
        String arguments = "";
        try {
            command = request.substring(0, request.indexOf(' '));
            arguments = request.substring(request.indexOf(' ') + 1);
        }
        catch (Exception e){
            command = "/show_tasks";
        }
        if(command.startsWith("/")){
            if(!COMMANDS.contains(command)){
                response = "Command not implemented yet";
            }
            else{
                if(command.equals("/add")){
                    String request_description = StringUtils.substringBetween(arguments, "\"", "\"");
                    this.addTask(request_description);
                }
                if (command.equals("/del")){
                    String[] strIdsForDelete = arguments.split(",");
                    Integer[] idsForDelete = new Integer[strIdsForDelete.length];
                    for(int i = 0; i < idsForDelete.length; i++){
                        idsForDelete[i] = Integer.parseInt(strIdsForDelete[i]);
                    }
                    this.deleteTask(idsForDelete);
                }
                if(command.equals("/show_tasks")) { this.showTasks(); }
            }
        }
        else{
            response = "Your command must starts with '\\' symbol";
        }
    }

    private void addTask(String description) {
        if (description == null || description.equals(" ") || description.equals("")) {
            response = "Please enter a task description";
        } else{
            Task task = new Task(description);
            tasks.add(task);
            response = String.format("Added task: %s", task.description);
        }
    }

    private void deleteTask(Integer[] ids){
        ArrayList<Integer> indexesListForDelete = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++){
            if (Arrays.asList(ids).contains(tasks.get(i).id)){
                indexesListForDelete.add(i);
            }
        }
        if (indexesListForDelete.isEmpty()){
            response = String.format("There is no task with ids: %s", Arrays.toString(ids));
        } else {
            Collections.sort(indexesListForDelete, Collections.reverseOrder());
            for (int i : indexesListForDelete) {
                tasks.remove(i);
            }
            response = String.format("Successfully deleted tasks with id: %s", Arrays.toString(ids));
        }
    }

    private void showTasks() {
        if (tasks.size() == 0) {
            response = "Congratulations! You don't have any tasks yet";
        } else {
            StringBuilder formattedTasks = new StringBuilder("Id\tОписание\n");
            for (int i = 0; i < tasks.size(); i++) {
                if (i == tasks.size() - 1){
                    formattedTasks.append(String.format("%d\t%s", tasks.get(i).id, tasks.get(i).description));
                }
                else{
                    formattedTasks.append(String.format("%d\t%s%n", tasks.get(i).id, tasks.get(i).description));
                }
            }
            response = formattedTasks.toString();
        }
    }
}