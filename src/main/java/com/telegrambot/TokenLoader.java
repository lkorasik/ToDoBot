package com.telegrambot;

import java.io.File;
import java.util.Scanner;

public class TokenLoader {
    private String token = null;
    private String name = null;

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

    public String getToken(){
        return token;
    }

    public String getName(){
        return name;
    }
}
