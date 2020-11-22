package com.core;

/**
 * Класс, в котором хранятся текстовое описание команд
 * и сообщений
 */
public class Constants {
    public static final String ADD_TASK_COMMAND = "/add";
    public static final String DELETE_TASK_COMMAND = "/del";
    public static final String COMPLETE_TASK_COMMAND = "/done";
    public static final String SHOW_COMPLETED_TASKS_COMMAND = "/showdone";
    public static final String SHOW_TODO_TASKS_COMMAND = "/showtodo";
    public static final String START_COMMAND = "/start";
    public static final String HELP_COMMAND = "/help";
    public static final String CANCEL_COMMAND = "/cancel";
    public static final String CLEAR_COMMAND = "/clear";

    public static final String HELP_MSG =
            "/add - You can add task. In next message send your task.\n" +
            "/del - You can delete task. In next message send task's number\n" +
            "/done - You can mark task as completed. In next message send task's number\n" +
            "/showtodo - You can see all tasks that you need to solve\n" +
            "/showdone - You can see all tasks that you have done\n" +
            "/help - You will see this message\n" +
            "/clear - You can clear your both task lists\n" +
            "/cancel - You can use this command if you want to cancel action such as add task or delete task.\n";
    public static final String START_MSG = "Hello, I'm telegram bot that can help to manage your tasks. " +
            "There is all commands that you can type to operate with me:\n" + HELP_MSG;
    public static final String EMPTY_TASK_DESCRIPTION_MSG = "Please enter not empty task description";
    public static final String TASK_DESCRIPTION_MSG = "Please enter task description";
    public static final String EMPTY_TASK_ID_MSG = "Please enter not empty task id";
    public static final String TASK_ID_MSG = "Please, enter task id";
    public static final String TASK_ADDED_MSG = "Added task: ";
    public static final String TASK_DELETED_MSG = "Successfully deleted task with id: ";
    public static final String TASK_COMPLETED_MSG = "Successfully marked task as done with id: ";
    public static final String CLEARED_TASK_LIST_MSG = "Successfully cleared the task list";
    public static final String NOT_EXISTING_TASK_ID_EXCEPTION_MSG = "There is no task with id: ";
    public static final String INCORRECT_TASK_ID_TYPE_EXCEPTION_MSG = "Please enter tasks id, not description";
    public static final String EMPTY_TODO_TASK_LIST_MSG = "Congratulations! You don't have any tasks";
    public static final String EMPTY_COMPLETED_TASK_LIST_MSG = "You haven't done any tasks yet";
    public static final String INCORRECT_COMMAND_MESSAGE = "Incorrect command";
    public static final String EP_MSG = "Enter " + Constants.START_COMMAND;
    public static final String BOT_WAITING_COMMANDS = "I'm waiting your commands";

    public static final String ADD_TASK_BUTTON = "Add task";
    public static final String DEL_TASK_BUTTON = "Del task";
    public static final String DONE_TASK_BUTTON = "Done";
    public static final String SHOW_TASKS_BUTTON = "Show tasks";
    public static final String SHOW_DONE_TASKS_BUTTON = "Show done tasks";
    public static final String CANCEL_BUTTON = "Cancel";

    public static final String TELEGRAM_TOKEN_FILENAME = "Token";

    public static final String LOGIN_MESSAGE = "Please input your login:";
    public static final String ENTRY_POINT_GREETINGS_MSG = "You successfully logged in. Type `/start` to start using the bot.";
    public static final String NOT_ENTRY_POINT_GREETINGS_MSG = "You successfully logged in. You can continue using the bot.";
    public static final String USERS_FILE = "users.json";
}