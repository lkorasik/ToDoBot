package com.authentication;

import java.util.HashMap;

/**
 * Класс, отвечающий за аутентификацию пользователей
 * @author Dmitry
 */
public class Authenticator {
    private HashMap<String, String> users = new HashMap<>();

    public boolean hasAccount(String id){
        return users.containsKey(id);
    }

    public boolean signIn(String userId, String pass){
        if (hasAccount(userId)){ return users.get(userId).equals(pass); }
        return false;
    }

    public void signUp(String userId, String pass){
        users.put(userId, pass);
    }
}
