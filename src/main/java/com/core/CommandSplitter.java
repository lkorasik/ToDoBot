/*
    Lev
 */
package com.core;

public class CommandSplitter {
    /**
     * Распарсить строчку. Выделить команду и тело команды
     * @param line - Строка с запросом
     * @return Команда
     */
    public static ParsedCommand split(String line){
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
}
