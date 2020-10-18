package com.telegrambot;

import java.io.File;
import java.util.Scanner;

/**
 * @author Lev
 *
 * Класс, который загружает из файла токены. Токены
 * будут использоваться для подключению к телеграму.
 */
public class TokenLoader {
    private String token = null;
    private String name = null;

    /**
     * Считываем токены
     */
    public TokenLoader(){
        try{
            File file = new File("Token");
            Scanner myReader = new Scanner(file);

            token = myReader.nextLine();
            name = myReader.nextLine();

            myReader.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Получить токен
     * @return Токен
     */
    public String getToken(){
        return token;
    }

    /**
     * Получить имя бота
     * @return Имя
     */
    public String getName(){
        return name;
    }
}
