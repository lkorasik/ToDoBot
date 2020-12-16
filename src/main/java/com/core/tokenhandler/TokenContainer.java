package com.core.tokenhandler;

class TokensContainer{
    private String telegramToken = null;
    private String discordToken = null;
    private String botName = null;

    public void setTelegramToken(String newValue){
        telegramToken = newValue;
    }

    public void setDiscordToken(String newValue){
        discordToken = newValue;
    }

    public void setBotName(String newValue){
        botName = newValue;
    }

    public String getTelegramToken(){
        return telegramToken;
    }

    public String getDiscordToken(){
        return discordToken;
    }

    public String getBotName(){
        return botName;
    }
}