/*
    Lev
 */
package com.core;

public class ParsedCommand {
    private final String command;
    private final String body;

    /**
     * Контейнер для команд
     * @param command - Команда (e.g. /add)
     * @param body - Тело команды (e.g. Task1)
     */
    public ParsedCommand(String command, String body){
        this.command = command;
        this.body = body;
    }

    /**
     * Получить команду
     * @return Команда
     */
    public String getCommand() {
        return command;
    }

    /**
     * Получить тело команды
     * @return Тело команды. Если команда была без тела, то вернется null
     */
    public String getBody() {
        return body;
    }
}
