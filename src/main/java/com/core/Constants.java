package com.core;

/**
 * Класс, в котором хранятся текстовое описание команд
 * и сообщений
 */
public class Constants {
    public static final String ADD_TASK_COMMAND = "/add";
    public static final String DELETE_TASK_COMMAND = "/del";
    public static final String SHOW_TASKS_COMMAND = "/show";
    public static final String START_COMMAND = "/start";
    public static final String HELP_COMMAND = "/help";
    public static final String CANCEL_COMMAND = "/cancel";

    public static final String HELP_MSG = "/add - You can add task.\n\ttext - task's text\n" +
            "/del - You can delete task.\n\ttask_id - Task's id\n" +
            "/show - You can see all tasks\n" +
            "/start - You can start chatting with bot\n" +
            "/help - You will see this message";
    public static final String START_MSG = "Hello, I'm telegram bot that can help to manage your tasks. " +
            "There is all commands that you can type to operate with me:\n" + HELP_MSG;
    public static final String NOT_IMPLEMENTED_COMMAND_MSG = "This command isn't implemented yet";
    public static final String INCORRECT_COMMAND_FORMAT_MSG = "Your command must starts with /";
    public static final String EMPTY_TASK_DESCRIPTION_MSG = "Please enter not empty task description";
    public static final String TASK_DESCRIPTION_MSG = "Please, enter task description";
    public static final String EMPTY_TASK_ID_MSG = "Please enter not empty task id";
    public static final String TASK_ID_MSG = "Please, enter task id";
    public static final String TASK_ADDED_MSG = "Added task: ";
    public static final String TASK_DELETED_MSG = "Successfully deleted task with id: ";
    public static final String NOT_EXISTING_TASK_ID_EXCEPTION_MSG = "There is no task with id: ";
    public static final String INCORRECT_TASK_ID_TYPE_EXCEPTION_MSG = "Please enter tasks id, not description";
    public static final String EMPTY_TASK_LIST = "Congratulations! You don't have any tasks yet";

    public static final String TELEGRAM_TOKEN_FILENAME = "Token";

    public static final String ENTRY_POINT_STATE = "ENTRY POINT";
    public static final String START_STATE = "START";
    public static final String ADD_STATE = "ADD";
    public static final String DEL_STATE = "DEL";
    public static final String SHOW_STATE = "SHOW";
    public static final String HELP_STATE = "HELP";
}