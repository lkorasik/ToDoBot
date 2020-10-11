package com.core;

public class CommandsNames {
    public static final String addTask = "/add";
    public static final String deleteTask = "/del";
    public static final String showTasks = "/show";
    public static final String start = "/start";
    public static final String help = "/help";

    public static final String startMsg = "I'm ready for work!";
    public static final String helpMsg = "/add [text] - You can add task.\n\ttext - task's text\n" +
            "/del [task_id] - You can delete task.\n\ttask_id - Task's id\n" +
            "/show - You can see all tasks\n" +
            "/start - You can start chating with bot\n" +
            "/help - You will see this message";
    public static final String notImplementedCommandMsg = "This command isn't implemented yet";
    public static final String incorrectCommandFormatMsg = "Your command must starts with /";
}
