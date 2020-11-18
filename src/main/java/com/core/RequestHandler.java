package com.core;

import com.fsm.FSM;
import com.fsm.State;
import org.apache.commons.lang3.StringUtils;

/**
 * Класс, который является прослойкой между ConsoleBot или Bot и Core
 *
 * @author Lev
 */
public class RequestHandler {
    private Core core = new Core();
    private FSM fsm = new FSM();

    /**
     * Проверка на то, что тело команды задано корректно
     * @param body Тело команды
     * @return True, если все хорошо
     */
    private boolean bodyIsCorrect(String body) {
        return body != null && !StringUtils.isBlank(body) && !body.equals("");
    }

    public State getFSMState(){
        return fsm.getCurrentState();
    }

    /**
     * Обработка сообщения
     * @param input - сообщение
     * @return Строка с резульатом, которую надо показать пользователю
     */
    public String handle(String uid, String input) {
        if (input.equals("/fsmstate"))
            return fsm.getCurrentState().toString();

        String res = null;
        boolean isAdd = fsm.isState(State.ADD);
        boolean isDel = fsm.isState(State.DEL);
        boolean isClear = fsm.isState(State.CLEAR);
        boolean isShow = fsm.isState(State.SHOW);
        boolean isHelp = fsm.isState(State.HELP);
        boolean isStart = fsm.isState(State.START);
        boolean isCancel = input.equals(Constants.CANCEL_COMMAND);

        if (isShow || isHelp || isStart || isClear)
            fsm.update();

        fsm.update(input);

        if(isAdd && !input.equals(Constants.CANCEL_COMMAND)){
            res = addTask(uid, input);
        }
        else if (isDel && !input.equals(Constants.CANCEL_COMMAND)){
            res = deleteTask(uid, input);
        }
        else if (fsm.isState(State.SHOW)){
            res = core.getTasks(uid);
        }
        else if (fsm.isState(State.CLEAR)){
            res = clearTaskList(uid);
        }
        else if(fsm.isState(State.LISTEN)){
            if (!isCancel)
                res = Constants.INCORRECT_COMMAND_MESSAGE;
            else
                res = Constants.BOT_WAITING_COMMANDS;
        }
        else {
            res = fsm.getCurrentState().getState();
        }

        return res;
    }

    /**
     * Обертка над методом addTask класса Core.
     *
     * @param body Текст задачи
     * @return Сообщение об результате операции
     */
    private String addTask(String uid, String body){
        String result;

        if (bodyIsCorrect(body)) {
            core.addTask(uid, body);
            result = Constants.TASK_ADDED_MSG + body;
        } else {
            result = Constants.EMPTY_TASK_DESCRIPTION_MSG;
        }

        return result;
    }

    /**
     * Обертка над методом deleteTask класса Core
     *
     * @param body Принимается идентификатор задачи
     * @return Сообщение об результате операции
     */
    private String deleteTask(String uid, String body){
        String result;

        if (bodyIsCorrect(body)) {
            try {
                core.deleteTask(uid, body);
                result = Constants.TASK_DELETED_MSG + body;
            } catch (NotExistingTaskIndexException | IncorrectTaskIdTypeException exception) {
                result = exception.getMessage();
            }
        } else {
            result = Constants.EMPTY_TASK_ID_MSG;
        }

        return result;
    }

    /**
     * Обертка над методом emptyTaskList класса Core
     *
     * @param uid Id пользователя, у которого нужно очистить список задач
     * @return Сообщение об результате операции
     */
    private String clearTaskList(String uid){
        core.clearTaskList(uid);

        return Constants.CLEARED_TASK_LIST_MSG;
    }
}

