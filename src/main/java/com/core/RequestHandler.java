package com.core;

import com.fsm.FSM;
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

    /**
     * Обработка сообщения
     * @param input - сообщение
     * @return Строка с резульатом, которую надо показать пользователю
     */
    public String handle(String uid, String input) {
        String res;
        boolean isAdd = fsm.getCurrentStateName().equals(Constants.ADD_STATE);
        boolean isDel = fsm.getCurrentStateName().equals(Constants.DEL_STATE);
        boolean isShow = fsm.getCurrentStateName().equals(Constants.SHOW_STATE);
        boolean isHelp = fsm.getCurrentStateName().equals(Constants.HELP_STATE);

        if (isShow)
            fsm.update();
        if (isHelp)
            fsm.update();

        fsm.update(input);

        if(isAdd && !input.equals(Constants.CANCEL_COMMAND)){
            res = addTask(uid, input);
        }
        else if (isDel && !input.equals(Constants.CANCEL_COMMAND)){
            res = deleteTask(uid, input);
        }
        else {
            res = fsm.getCurrentStateName();

            switch (res) {
                case Constants.START_STATE:
                    res = Constants.START_MSG;
                    break;
                case Constants.HELP_STATE:
                    res = Constants.HELP_MSG;
                    break;
                case Constants.ADD_STATE:
                    res = Constants.TASK_DESCRIPTION_MSG;
                    break;
                case Constants.DEL_STATE:
                    res = Constants.TASK_ID_MSG;
                    break;
                case Constants.SHOW_STATE:
                    res = core.getTasks(uid);
                    break;
            }
        }

        return res;
    }

    /**
     * Добавить задачу в список
     * @param body Текст задачи
     * @return Результат, который надо показать пользователю
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
     * Удаление задачи
     * @param body Принимается идентификатор задачи
     * @return Результат, который надо вывести пользователю
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
}

