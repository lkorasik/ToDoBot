package com.core;

import com.fsm.State;
import org.apache.commons.lang3.StringUtils;
import java.text.ParseException;

/**
 * Класс, который является прослойкой между ConsoleBot или Bot и Core
 *
 * @author Lev
 */
public class RequestHandler {
    private Core core;

    public RequestHandler(){
        core = new Core();
    }

    public RequestHandler(String path){
        core = new Core(path);
    }

    public boolean isUserSignedIn(String uid){
        try{
            getUserFSMState(uid);
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * Получение текущего состояния пользователя
     * @param uid Id пользователя
     */
    public State getUserFSMState(String uid){
        return core.getUserFsmState(uid);
    }

    /**
     * Проверка на то, что тело команды задано корректно
     * @param body Тело команды
     * @return True, если все хорошо
     */
    private boolean bodyIsCorrect(String body) {
        return body != null && !StringUtils.isBlank(body) && !body.equals("");
    }

    /**
     * Обработка сообщения
     * @param input - сообщение
     * @return Строка с резульатом, которую надо показать пользователю
     */
    public String handle(String uid, String chatId, String input, ISender sender) {
        if (!isUserSignedIn(uid)) {
            core.createUser(uid);
        }

        String res = null;
        if (input.equals("/fsmstate"))
            return core.getUserFsmState(uid).toString();

        var currentState = core.getUserFsmState(uid);
        var newState = core.updateUserFsmState(uid, input);

        switch (currentState) {
            case ADD:
                if (input.equals("/cancel")) {
                    res = Constants.BOT_WAITING_COMMANDS;
                } else {
                    res = addTask(uid, input);
                }
                break;
            case DEL:
                if (input.equals("/cancel")) {
                    res = Constants.BOT_WAITING_COMMANDS;
                } else {
                    res = deleteTask(uid, input);
                }
                break;
            case DONE:
                if (input.equals("/cancel")) {
                    res = Constants.BOT_WAITING_COMMANDS;
                } else {
                    res = completeTask(uid, input);
                }
                break;
            case NOTIFICATION:
                if (input.equals("/cancel")) {
                    res = Constants.BOT_WAITING_COMMANDS;
                } else {
                    var converter = new DateConverter();
                    var date = converter.parse(getTime(input));
                    if (date != null) {
                        core.setTimer(uid, chatId, Integer.parseInt(getTaskId(input)), date, sender);
                        res = Constants.NOTIFICATION_ADDED_MSG;
                    } else {
                        res = Constants.NOTIFICATION_NOT_ADDED_MSG;
                    }
                }
                break;
        }
        if (res == null){
            switch (newState){
                case LISTEN:
                    res = Constants.INCORRECT_COMMAND_MESSAGE;
                    break;
                case SHOW_COMPLETED:
                    res = core.getFormattedCompletedTasksString(uid);
                    break;
                case SHOW_TODO:
                    res = core.getFormattedToDoTasks(uid);
                    break;
                case CLEAR:
                    res = clearTaskList(uid);
                    break;
                default:
                    res = core.getUserFsmState(uid).getStateMessage();
            }
        }

        return res;
    }

    private String getTaskId(String message){
        int position = message.indexOf(" ");

        return message.substring(0, position);
    }

    private String getTime(String message){
        int position = message.indexOf(" ");

        return message.substring(position + 1);
    }

    /**
     * Обертка над методом addTask класса Core.
     *
     * @param taskDescription Текст задачи
     * @return Сообщение с результатом операции
     */
    private String addTask(String uid, String taskDescription){
        String result;

        if (bodyIsCorrect(taskDescription)) {
            core.addTask(uid, taskDescription);
            result = Constants.TASK_ADDED_MSG + taskDescription;
        } else {
            result = Constants.EMPTY_TASK_DESCRIPTION_MSG;
        }

        return result;
    }

    /**
     * Обертка над методом deleteTask класса Core
     *
     * @param taskId Принимается идентификатор задачи
     * @return Сообщение с результатом операции
     */
    private String deleteTask(String uid, String taskId){
        String result;

        if (bodyIsCorrect(taskId)) {
            try {
                core.deleteTask(uid, taskId);
                result = Constants.TASK_DELETED_MSG + taskId;
            } catch (NotExistingTaskIndexException | IncorrectTaskIdTypeException exception) {
                result = exception.getMessage();
            }
        } else {
            result = Constants.EMPTY_TASK_ID_MSG;
        }

        return result;
    }

    /**
     * Обертка над методом completeTask класса Core
     *
     * @param uid Id пользователя
     * @param taskId Id задачи
     * @return Сообщение с результатом операции
     */
    public String completeTask(String uid, String taskId){
        String result;

        if (bodyIsCorrect(taskId)) {
            try {
                core.completeTask(uid, taskId);
                result = String.format(Constants.TASK_COMPLETED_MSG, taskId);
            } catch (IncorrectTaskIdTypeException | NotExistingTaskIndexException exception) {
                result = exception.getMessage();
            }
        } else {
            result  = Constants.EMPTY_COMPLETED_TASK_LIST_MSG;
        }

        return result;
    }

    /**
     * Обертка над методом emptyTaskList класса Core
     *
     * @param uid Id пользователя, у которого нужно очистить список задач
     * @return Сообщение с результатом операции
     */
    private String clearTaskList(String uid){
        core.clearAllTaskLists(uid);

        return Constants.CLEARED_TASK_LIST_MSG;
    }
}

