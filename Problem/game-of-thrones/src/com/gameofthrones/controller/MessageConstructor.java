package com.gameofthrones.controller;

import com.gameofthrones.model.Kingdom;
import com.gameofthrones.model.Message;

import java.util.List;

public interface MessageConstructor {
    List<Message> constructMessages(Kingdom sender);
}
