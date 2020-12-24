package com.consolebot;

import com.core.Constants;

import java.text.ParseException;
import java.util.Scanner;

/**
 * Класс, который запускает консольную версию бота
 *
 * @author Dmitry
 */
public class StartConsoleBot {

    public static void main(String[] args) throws ParseException {
        var scanner = new Scanner(System.in);
        var userId = "";
        while (userId.equals("")) {
            System.out.println(Constants.LOGIN_MESSAGE);
            userId = scanner.nextLine();
        }
        var bot = new ConsoleBot(userId);
        bot.printGreetingsMessage();
        bot.run();
    }
}