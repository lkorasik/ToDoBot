package com.consolebot;

import com.core.RequestHandler;

import java.util.Scanner;

/**
 * Класс для взаимодействия с ботом через CLI
 * @author Dmitry
 */
public class ConsoleBot {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RequestHandler requestHandler = new RequestHandler();
        while (true) {
            System.out.println(requestHandler.handle(scanner.nextLine()));
        }
    }
}
