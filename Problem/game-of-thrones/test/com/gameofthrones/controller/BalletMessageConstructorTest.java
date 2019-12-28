package com.gameofthrones.controller;

import com.gameofthrones.controller.helpers.KingdomFactory;
import com.gameofthrones.controller.helpers.MessageContentGenerator;
import com.gameofthrones.model.Kingdom;
import com.gameofthrones.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BalletMessageConstructorTest {

    private Kingdom sender;
    private MessageContentGenerator messageContentGenerator;

    private BalletMessageConstructor messageConstructor;

    @BeforeEach
    void setUp() {
        sender = mock(Kingdom.class);
        messageContentGenerator = mock(MessageContentGenerator.class);
        messageConstructor = new BalletMessageConstructor(messageContentGenerator);
    }

    @Test
    void returnEmptyMessageListWhenSenderIsNull() {
        List<Message> messages = messageConstructor.constructMessages(null);

        assertTrue(messages.isEmpty());
    }

    @Test
    void constructedMessageListShouldNotBeEmpty() {
        List<Message> messages = messageConstructor.constructMessages(sender);

        assertFalse(messages.isEmpty());
    }

    @Test
    void constructAllMessagesWithGivenSender() {
        List<Message> messages = messageConstructor.constructMessages(sender);

        messages.forEach(message -> {
            assertEquals(sender, message.getSender());
        });
    }

    @Test
    void constructMessagesWithAllKingdomsExceptSelfAsReceivers() {
        List<Kingdom> kingdoms = new ArrayList<>(KingdomFactory.kingdomMap.values());
        Kingdom sender = kingdoms.get(0);
        kingdoms.remove(sender);

        List<Message> messages = messageConstructor.constructMessages(sender);

        List<Kingdom> receivers = messages.stream().map(Message::getReceiver).collect(Collectors.toList());
        assertTrue(receivers.containsAll(kingdoms));
    }

    @Test
    void constructMessagesWithRandomMessageContent() {
        List<Kingdom> kingdoms = new ArrayList<>(KingdomFactory.kingdomMap.values());
        Kingdom sender = kingdoms.get(0);
        kingdoms.remove(sender);
        String randomContent = "Random Content";
        when(messageContentGenerator.getRandomMessageContent()).thenReturn(randomContent);

        List<Message> messages = messageConstructor.constructMessages(sender);

        messages.forEach(message -> {
            assertEquals(randomContent, message.getContent());
        });
    }
}
