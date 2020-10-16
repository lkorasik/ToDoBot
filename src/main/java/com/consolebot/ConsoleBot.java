/*
    Dmitry
 */
package com.consolebot;

import com.core.RequestHandler;
import com.core.Core;
//import com.core.CommandsNames;
import com.core.ParsedCommand;

import java.util.Scanner;

/**
 * Класс для взаимодействия с ботом через CLI
 */
public class ConsoleBot {
    public static void main(String [] args){

        Core core = new Core();
        Scanner scanner = new Scanner(System.in);
        while (true){
            RequestHandler requestHandler = new RequestHandler();
            //ParsedCommand parsedMessage = requestHandler.split(scanner.nextLine());
            //System.out.println(requestHandler.handle(core, parsedMessage));
        }
    }
}
