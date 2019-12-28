package com.gameofthrones.controller;

import com.gameofthrones.model.Kingdom;
import com.gameofthrones.model.Message;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class BalletBox {
    private MessageConstructor messageConstructor;
    private List<Message> messages;

    public BalletBox(MessageConstructor messageConstructor) {
        this.messageConstructor = messageConstructor;
    }

    public void acceptMessages(List<Kingdom> candidates){
        if(isNull(candidates)){
            return;
        }
        messages = new ArrayList<>();
        candidates.forEach(candidate -> {
            messages.addAll(messageConstructor.constructMessages(candidate));
        });
    }

    public List<Message> getMessages() {
        return messages;
    }
}
