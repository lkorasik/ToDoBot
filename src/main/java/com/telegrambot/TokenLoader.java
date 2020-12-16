package com.telegrambot;

import com.core.Constants;
import org.checkerframework.checker.signedness.qual.Constant;

import java.io.File;
import java.util.Scanner;

/**
 * @author Lev
 *
 * Класс, который загружает из файла токены. Токены
 * будут использоваться для подключению к телеграму.
 */
public class TokenLoader {
    private String telegramToken = null;
    private String discordToken = null;
    private String name = null;

    /**
     * Считываем токены
     */
    public TokenLoader(){
        try{
            File file = new File(Constants.TELEGRAM_TOKEN_FILENAME);
            Scanner myReader = new Scanner(file);

            discordToken = myReader.nextLine();
            telegramToken = myReader.nextLine();
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
    public String getTelegramToken(){
        return telegramToken;
    }

    public String getDiscordToken(){
        return discordToken;
    }

    /**
     * Получить имя бота
     * @return Имя
     */
    public String getName(){
        return name;
    }
}
