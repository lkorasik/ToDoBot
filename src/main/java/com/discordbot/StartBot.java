package com.discordbot;

import com.telegrambot.TokenLoader;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class StartBot {
    public static void main(String[] args) throws LoginException {
        var tokenLoader = new TokenLoader();

        var jda = JDABuilder.createDefault(tokenLoader.getDiscordToken()).build();
        jda.addEventListener(new Bot());
    }
}
