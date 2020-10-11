/*
    Dmitry
 */
package com.core;

public class Task
{
    public String description;
    private static int id_counter = 0;
    public int id;

    public Task(String description)
    {
        id = Task.id_counter + 1;
        Task.id_counter += 1;
        this.description = description;
    }
}
