package com.consolebot;

import com.core.Constants;
import com.core.RequestHandler;
import com.fsm.State;

import java.net.Authenticator;
import java.util.Scanner;

/**
 * Класс для взаимодействия с ботом через CLI
 * @author Dmitry
 */
public class ConsoleBot {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RequestHandler requestHandler = new RequestHandler();
        String userId = Authenticate();
        while (true) {
            System.out.println(requestHandler.handle(userId, scanner.nextLine()));
        }
    }

    public static String Authenticate(){
        RequestHandler requestHandler = new RequestHandler();
        Scanner scanner = new Scanner(System.in);
        String userId = "";
        while (userId.equals("")){
            System.out.println(Constants.LOGIN_MESSAGE);
            userId = scanner.nextLine();
        }
        if (requestHandler.getUserFSMState(userId) == null){
            System.out.println(Constants.ENTRY_POINT_GREETINGS_MSG);
        } else {
            System.out.println(Constants.NOT_ENTRY_POINT_GREETINGS_MSG);
        }

        return userId;
    }
}
