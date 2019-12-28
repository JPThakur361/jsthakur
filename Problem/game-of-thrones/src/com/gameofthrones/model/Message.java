package com.gameofthrones.model;

//Represents an entity of communication between kingdoms
public class Message {
    private Kingdom sender, receiver;
    private String content;

    public Message(Kingdom sender, Kingdom receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    public Kingdom getSender() {
        return sender;
    }

    public Kingdom getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }
}
