package com.core.tokenhandler;

import com.core.Constants;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

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
    public TokenLoader() {
        File file = new File(Constants.TOKEN_FILENAME);
        String content = null;

        try {
            content = FileUtils.readFileToString(file, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }

        TokensContainer container = new Gson().fromJson(content, (Type) TokensContainer.class);

        if(container != null) {
            telegramToken = container.getTelegramToken();
            discordToken = container.getDiscordToken();
            name = container.getBotName();
        }
    }

    /**
     * Получить токен для телеграма
     * @return Токен
     */
    public String getTelegramToken(){
        return telegramToken;
    }

    /**
     * Получить токен для дискорда
     * @return
     */
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


