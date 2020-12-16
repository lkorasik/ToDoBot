package com.discordbot;

import com.core.ISender;
import com.core.RequestHandler;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends ListenerAdapter implements ISender {
    private List<MessageChannel> channels = new ArrayList<>();

    private final RequestHandler requestHandler = new RequestHandler();

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        if (event.getAuthor().isBot()) return;

        String answer = null;

        try {
            answer = handleMessage(event);
        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }

        if (answer != null){
            var channel = event.getChannel();

            if (!channels.contains(channel)){
                channels.add(channel);
            }

            channel.sendMessage(answer).queue();
        }
    }

    private String handleMessage(MessageReceivedEvent event) throws ParseException {
        String message = event.getMessage().getContentRaw();
        var channel = event.getChannel();

        var uid = event.getAuthor().getId();

        return requestHandler.handle(uid, channel.getId(), message, this);
    }

    @Override
    public void print(String chatId, String message) {
        for(var channel : channels){
            if(channel.getId().equalsIgnoreCase(chatId)){
                channel.sendMessage(message).queue();
                return;
            }
        }
    }
}
