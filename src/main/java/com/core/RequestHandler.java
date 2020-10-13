/*
    Lev
 */
package com.core;

import org.apache.commons.lang3.StringUtils;

public class RequestHandler {
    /**
     * Распарсить строчку. Выделить команду и тело команды
     *
     * @param line - Строка с запросом
     * @return Команда
     */
    public ParsedCommand split(String line) {
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

    public boolean bodyIsCorrect(String body) {
        if (body == null || StringUtils.isBlank(body) || body.equals("")) {
            return false;
        }
        return true;
    }

    public String handle(Core core, ParsedCommand parsedCommand) {
        String result;

        String command = parsedCommand.getCommand();
        String body = parsedCommand.getBody();

        if (command.startsWith("/")) {
            switch (command) {
                case CommandsNames.start:
                    result = CommandsNames.startMsg;
                    break;
                case CommandsNames.help:
                    result = CommandsNames.helpMsg;
                    break;
                case CommandsNames.addTask:
                    if (bodyIsCorrect(body)) {
                        core.addTask(body);
                        result = String.format("Added task: %s", body);
                    } else {
                        result = "Please enter not empty task description";
                    }
                    break;
                case CommandsNames.deleteTask:
                    if (bodyIsCorrect(body)) {
                        try {
                            core.deleteTask(body);
                            result = String.format("Successfully deleted task with id: %s", body);
                        } catch (notExistingTaskIndexException | incorrectTaskIdTypeException exception) {
                            result = exception.toString();
                        }
                    } else {
                        result = "Please enter not empty task id";
                    }
                    break;
                case CommandsNames.showTasks:
                    result = core.showTasks();
                    break;
                default:
                    result = CommandsNames.notImplementedCommandMsg;
                    break;
            }
        } else {
            result = CommandsNames.incorrectCommandFormatMsg;
        }

        return result;
    }
}

