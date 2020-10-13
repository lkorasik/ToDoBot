/*
    Lev
 */
package com.core;

public class RequestHandler {
    /**
     * Распарсить строчку. Выделить команду и тело команды
     * @param line - Строка с запросом
     * @return Команда
     */
    public ParsedCommand split(String line){
        var position = line.indexOf(" ");

        String command = null;
        String body = null;

        if (position == -1){
            command = line;
        }
        else{
            command = line.substring(0, position);
            body = line.substring(position + 1);
        }

        return new ParsedCommand(command, body);
    }

    public String handle(Core core, ParsedCommand parsedCommand){
        String result;

        String command = parsedCommand.getCommand();
        String body = parsedCommand.getBody();

        if(command.startsWith("/")){
            switch (command){
                case CommandsNames.start:
                    result = CommandsNames.startMsg;
                    break;
                case CommandsNames.help:
                    result = CommandsNames.helpMsg;
                    break;
                case CommandsNames.addTask:
                    result = core.addTask(body);
                    break;
                case CommandsNames.deleteTask:
                    result = core.deleteTask(body);
                    break;
                case CommandsNames.showTasks:
                    result = core.showTasks();
                    break;
                default:
                    result = CommandsNames.notImplementedCommandMsg;
                    break;
            }
        }
        else {
            result = CommandsNames.incorrectCommandFormatMsg;
        }

        return result;
    }
}
