package com.consolebot;

import com.core.Core;
import java.util.Scanner;

public class ConsoleBot {
    public static void main(String [] args){
        Core bot = new Core();
        Scanner scanner = new Scanner(System.in);
        while (true){
            String userInput = scanner.nextLine();
            bot.sendRequest(userInput);
            System.out.println(bot.response);
        }
    }
}
