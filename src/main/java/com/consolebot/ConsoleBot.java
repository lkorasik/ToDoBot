package com.consolebot;

import com.core.Constants;
import com.core.ISender;
import com.core.RequestHandler;
import java.text.ParseException;
import java.util.Scanner;

/**
 * Класс для взаимодействия с ботом через CLI
 * @author Dmitry
 */
public class ConsoleBot implements ISender {
    String userId;
    RequestHandler requestHandler;
    Scanner scanner;
    String chatId;

    public ConsoleBot(String userId){
        this.userId = userId;
        requestHandler = new RequestHandler();
        scanner = new Scanner(System.in);
        chatId = "consoleBotChat";

    }

    public void printGreetingsMessage() {
        if (requestHandler.isUserSignedIn(userId)){
            System.out.println(Constants.NOT_ENTRY_POINT_GREETINGS_MSG);
        } else {
            System.out.println(Constants.ENTRY_POINT_GREETINGS_MSG);
        }
    }
    public void run() throws ParseException {
        while (true) {
            System.out.println(requestHandler.handle(userId, chatId, scanner.nextLine(), this));
        }
    }

    public void print(String chatId, String message) {
        System.out.println(message);
    }
}
