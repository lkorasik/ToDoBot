package com.core;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Lev
 *
 * Класс, который является прослойкой между ConsoleBot или Bot и Core
 */
public class RequestHandler {
    private Core core = new Core();

    /**
     * Распарсить строчку. Выделить команду и тело команды
     *
     * @param line - Строка с запросом
     * @return Команда
     */
    private ParsedCommand split(String line) {
        var position = line.indexOf(" ");

        String command = null;
        String body = null;

        if (position == -1) {
            command = line;
        } else {
            command = line.substring(0, position);
            body = line.substring(position + 1);
        }

        return new ParsedCommand(command, body);
    }

    /**
     * Проверка на то, что тело команды задано корректно
     * @param body Тело команды
     * @return True, если все хорошо
     */
    private boolean bodyIsCorrect(String body) {
        if (body == null || StringUtils.isBlank(body) || body.equals("")) {
            return false;
        }
        return true;
    }

    /**
     * Обработка сообщения
     * @param parsedCommand - сообщение
     * @return Строка с резульатом, которую надо показать пользователю
     */
    public String handle(String input) {
        String result;

        ParsedCommand parsedCommand = split(input);

        String command = parsedCommand.getCommand();
        String body = parsedCommand.getBody();

        if (command.startsWith("/")) {
            switch (command) {
                case Constants.START_COMMAND:
                    result = Constants.START_MSG;
                    break;
                case Constants.HELP_COMMAND:
                    result = Constants.HELP_MSG;
                    break;
                case Constants.ADD_TASK_COMMAND:
                    result = addTask(body);
                    break;
                case Constants.DELETE_TASK_COMMAND:
                    result = deleteTask(body);
                    break;
                case Constants.SHOW_TASKS_COMMAND:
                    result = core.getTasks();
                    break;
                default:
                    result = Constants.NOT_IMPLEMENTED_COMMAND_MSG;
                    break;
            }
        } else {
            result = Constants.INCORRECT_COMMAND_FORMAT_MSG;
        }

        return result;
    }

    /**
     * Добавить задачу в список
     * @param body Текст задачи
     * @return Результат, который надо показать пользователю
     */
    private String addTask(String body){
        String result;

        if (bodyIsCorrect(body)) {
            core.addTask(body);
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
    private String deleteTask(String body){
        String result;

        if (bodyIsCorrect(body)) {
            try {
                core.deleteTask(body);
                result = Constants.TASK_DELETED_MSG + body;
            } catch (notExistingTaskIndexException | incorrectTaskIdTypeException exception) {
                result = exception.toString();
            }
        } else {
            result = Constants.EMPTY_TASK_ID_MSG;
        }

        return result;
    }
}

