package com.consolebot;

import com.core.RequestHandler;

import java.util.Scanner;

/**
 * @author Dmitry
 * Класс для взаимодействия с ботом через CLI
 */
public class ConsoleBot {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            RequestHandler requestHandler = new RequestHandler();
            System.out.println(requestHandler.handle(scanner.nextLine()));
        }
    }
}
