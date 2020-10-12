package com.core;

public class Task
{
    public String description;
    public static int id_counter = 0;
    private int id;

    public Task(String description)
    {
        id = Task.id_counter + 1;
        Task.id_counter += 1;
        this.description = description;
    }

    public int getId(){
        return id;
    }
}