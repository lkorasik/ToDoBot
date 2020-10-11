/*
    Dmitry
 */
package com.consolebot;

import com.core.CommandSplitter;
import com.core.Core;
import com.core.ParsedCommand;

import java.util.Scanner;

public class ConsoleBot {
    public static void main(String [] args){
        Core bot = new Core();
        Scanner scanner = new Scanner(System.in);
        while (true){
            String userInput = scanner.nextLine();
            ParsedCommand userArgs = CommandSplitter.Split(userInput);
            if (userArgs.getCommand().startsWith("/")){
                if (userArgs.getCommand().equals("/add")){
                    System.out.println(bot.addTask(userArgs.getBody()));
                }
                else if (userArgs.getCommand().equals("/del")){
                    System.out.println(bot.deleteTask(userArgs.getBody()));
                }
                else if (userArgs.getCommand().equals("/show_tasks")){
                    System.out.println(bot.showTasks());
                }
                else {
                    System.out.println("This command is not implemented yet");
                }
            } else{
                System.out.println("Your command must starts with /");
            }
        }
    }
}
