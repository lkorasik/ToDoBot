package com.consolebot;

import com.authentication.Authenticator;
import com.core.RequestHandler;
import org.checkerframework.checker.units.qual.A;

import java.util.Scanner;

/**
 * Класс для взаимодействия с ботом через CLI
 * @author Dmitry
 */
public class ConsoleBot {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RequestHandler requestHandler = new RequestHandler();
        Authenticator authenticator = new Authenticator();
        while (!authenticator.gotCredentials()){
            System.out.println(authenticator.getStatus());
            System.out.println(authenticator.authenticate(scanner.nextLine()));
        }
        while (true) {
            System.out.println(requestHandler.handle(authenticator.getUserId(), scanner.nextLine()));
        }
    }
}
