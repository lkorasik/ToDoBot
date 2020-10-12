/*
    Dmitry
 */
package com.consolebot;

import com.core.CommandSplitter;
import com.core.Core;
import com.core.CommandsNames;
import com.core.ParsedCommand;

import java.util.Scanner;

public class ConsoleBot {
    public static void main(String [] args){
        Core bot = new Core();
        Scanner scanner = new Scanner(System.in);
        while (true){
            ParsedCommand parsedUserInput = CommandSplitter.split(scanner.nextLine());
            String command = parsedUserInput.getCommand();
            if(command.startsWith("/")){
                switch (command){
                    case CommandsNames.start:
                        System.out.println(CommandsNames.startMsg);
                        break;
                    case CommandsNames.help:
                        System.out.println(CommandsNames.helpMsg);
                        break;
                    case CommandsNames.addTask:
                        System.out.println(bot.addTask(parsedUserInput.getBody()));
                        break;
                    case CommandsNames.deleteTask:
                        System.out.println(bot.deleteTask(parsedUserInput.getBody()));
                        break;
                    case CommandsNames.showTasks:
                        System.out.println(bot.showTasks());
                        break;
                    default:
                        System.out.println(CommandsNames.notImplementedCommandMsg);
                        break;
                }
            }
            else {
                System.out.println(CommandsNames.incorrectCommandFormatMsg);
            }
        }
    }
}
