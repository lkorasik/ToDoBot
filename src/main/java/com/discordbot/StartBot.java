package com.discordbot;

import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class StartBot {
    public static void main(String[] args) throws LoginException {
        var jda = JDABuilder.createDefault("NzgzMjg1OTA1MTI0ODg0NDkw.X8Yh2A.aG7djUmyEu9SiORMmJx8kEUv20g").build();
        jda.addEventListener(new Bot());
    }
}
