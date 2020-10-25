package com.authentication;

import java.util.HashMap;

/**
 * Класс, отвечающий за аутентификацию пользователей
 * @author Dmitry
 */
public class Authenticator {
    private String userId;

    public String getStatus(){
        if (userId == null){
            return "Please input your login:";
        }
        return "";
    }

    public String authenticate(String input){
        userId = input;
        return  "Now you can type /start to start using bot";
    }

    public boolean gotCredentials(){
        return userId != null;
    }

    public String getUserId(){
        return userId;
    }
}
