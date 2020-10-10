package com.consolebot;

import java.util.Scanner;

public class ConsoleBot {
    public static void main(String [] args){
        com.core.Core bot = new com.core.Core();
        Scanner scanner = new Scanner(System.in);
        while (true){
            String userInput = scanner.nextLine();
            bot.sendRequest(userInput);
            System.out.println(bot.response);
        }
    }
}
