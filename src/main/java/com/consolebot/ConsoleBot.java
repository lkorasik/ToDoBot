package com.consolebot;

import com.core.Constants;
import com.core.RequestHandler;
import java.text.ParseException;
import java.util.Scanner;

/**
 * Класс для взаимодействия с ботом через CLI
 * @author Dmitry
 */
public class ConsoleBot {
    public static void main(String[] args) throws ParseException {
        var scanner = new Scanner(System.in);
        var requestHandler = new RequestHandler();
        var userId = "";
        while (userId.equals("")){
            System.out.println(Constants.LOGIN_MESSAGE);
            userId = scanner.nextLine();
        }
        if (requestHandler.getUserFSMState(userId) == null){
            System.out.println(Constants.ENTRY_POINT_GREETINGS_MSG);
        } else {
            System.out.println(Constants.NOT_ENTRY_POINT_GREETINGS_MSG);
        }
        while (true) {
            System.out.println(requestHandler.handle(userId, "1", scanner.nextLine(), ConsoleBot::print));
        }
    }

    private static void print(String chatId, String message) {
        System.out.println(message);
    }
}
