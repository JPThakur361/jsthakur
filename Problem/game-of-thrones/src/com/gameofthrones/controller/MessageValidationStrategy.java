package com.gameofthrones.controller;

import com.gameofthrones.model.Message;

public interface MessageValidationStrategy {
    boolean isValid(Message message);
}
