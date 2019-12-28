package com.gameofthrones.controller;

import com.gameofthrones.model.Kingdom;
import com.gameofthrones.model.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;

public class ElectionCoordinator {
    private BalletBox balletBox;
    private List<Message> randomMessages;
    private int numberOfMessagesToBePicked;

    public ElectionCoordinator(BalletBox balletBox, int numberOfMessagesToBePicked) {
        this.balletBox = balletBox;
        this.numberOfMessagesToBePicked = numberOfMessagesToBePicked;
    }

    public void conductElection(List<Kingdom> candidates) {
        if (isNull(candidates)) {
            return;
        }
        candidates.forEach(candidate -> candidate.getAllies().clear());
        balletBox.acceptMessages(candidates);
        pickRandomMessages();
        distributeToReceivers();
    }

    private void pickRandomMessages() {
        List<Message> messages = balletBox.getMessages();
        if (isNull(messages) || messages.isEmpty() ||
                numberOfMessagesToBePicked > messages.size()) {
            this.randomMessages = messages;
            return;
        }

        Collections.shuffle(messages);
        this.randomMessages = new ArrayList<>(messages.subList(0, numberOfMessagesToBePicked));
    }

    private void distributeToReceivers() {
        if (isNull(randomMessages)) {
            return;
        }
        randomMessages.forEach(message -> {
            message.getReceiver().processAllyInvite(message);
        });
    }

    public List<Message> getRandomMessages() {
        return randomMessages;
    }

    public void setNumberOfMessagesToBePicked(Integer numberOfMessagesToBePicked) {
        this.numberOfMessagesToBePicked = numberOfMessagesToBePicked;
    }
}
