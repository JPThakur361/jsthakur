package com.gameofthrones.model;

import com.gameofthrones.controller.MessageValidationStrategy;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

//Represents a territory ruled by a king
public class Kingdom {
    private String name;
    private String emblem;
    private King ruler;
    private Set<Kingdom> allies;
    private MessageValidationStrategy messageValidationStrategy;

    public Kingdom(String name, String emblem) {
        this.name = name;
        this.emblem = emblem;
        this.allies = new LinkedHashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Kingdom)) return false;
        Kingdom kingdom = (Kingdom) o;
        return Objects.equals(getName(), kingdom.getName()) &&
                Objects.equals(getEmblem(), kingdom.getEmblem());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getEmblem());
    }

    public void sendMessages(List<Message> messages) {
        if (isNull(messages)) {
            return;
        }
        messages.forEach(message -> {
            message.getReceiver().processAllyInvite(message);
        });
    }

    public void processAllyInvite(Message message) {
        if (nonNull(messageValidationStrategy) && messageValidationStrategy.isValid(message)) {
            message.getSender().joinAllies(this);
        }
    }

    public void joinAllies(Kingdom kingdom) {
        if (Objects.isNull(kingdom) || kingdom.equals(this)) {
            return;
        }
        this.allies.add(kingdom);
    }

    public String getName() {
        return name;
    }

    public String getEmblem() {
        return emblem;
    }

    public King getRuler() {
        return ruler;
    }

    public void setRuler(King ruler) {
        this.ruler = ruler;
    }

    public Set<Kingdom> getAllies() {
        return allies;
    }

    public MessageValidationStrategy getMessageValidationStrategy(){
        return messageValidationStrategy;
    }

    public void setMessageValidationStrategy(MessageValidationStrategy messageValidationStrategy) {
        this.messageValidationStrategy = messageValidationStrategy;
    }
}
