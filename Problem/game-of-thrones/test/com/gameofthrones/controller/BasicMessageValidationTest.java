package com.gameofthrones.controller;

import com.gameofthrones.model.Kingdom;
import com.gameofthrones.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BasicMessageValidationTest {
    private BasicMessageValidation basicMessageValidation;

    @BeforeEach
    void setUp() {
        basicMessageValidation = new BasicMessageValidation();
    }

    @Test
    void aNullMessageIsNotValid() {
        assertFalse(basicMessageValidation.isValid(null));
    }

    @Test
    void aMessageWithoutReceiverIsNotValid() {
        Kingdom sender = mock(Kingdom.class);
        Message noReceiverMessage = mock(Message.class);
        when(noReceiverMessage.getSender()).thenReturn(sender);
        when(noReceiverMessage.getContent()).thenReturn("content");

        assertFalse(basicMessageValidation.isValid(noReceiverMessage));
    }

    @Test
    void aMessageWithoutSenderIsNotValid() {
        Kingdom receiver = mock(Kingdom.class);
        Message noSenderMessage = mock(Message.class);
        when(noSenderMessage.getReceiver()).thenReturn(receiver);
        when(noSenderMessage.getContent()).thenReturn("content");

        assertFalse(basicMessageValidation.isValid(noSenderMessage));
    }

    @Test
    void aMessageWithoutContentIsNotValid() {
        Kingdom receiver = mock(Kingdom.class);
        Kingdom sender = mock(Kingdom.class);
        Message noContentMessage = mock(Message.class);
        when(noContentMessage.getReceiver()).thenReturn(receiver);
        when(noContentMessage.getSender()).thenReturn(sender);

        assertFalse(basicMessageValidation.isValid(noContentMessage));
    }

    @Test
    void aMessageThatContainsEmblemOfReceiverInContentIsValid() {
        Kingdom sender = mock(Kingdom.class);
        Kingdom receiver = mock(Kingdom.class);
        when(receiver.getEmblem()).thenReturn("owl");
        Message validMessage = mock(Message.class);
        when(validMessage.getReceiver()).thenReturn(receiver);
        when(validMessage.getSender()).thenReturn(sender);
        when(validMessage.getContent()).thenReturn("oaaawaala");

        assertTrue(basicMessageValidation.isValid(validMessage));
    }

    @Test
    void aMessageThatDoesNotContainEmblemOfReceiverInContentIsInvalid() {
        Kingdom sender = mock(Kingdom.class);
        Kingdom receiver = mock(Kingdom.class);
        when(receiver.getEmblem()).thenReturn("owl");
        Message invalidMessage = mock(Message.class);
        when(invalidMessage.getReceiver()).thenReturn(receiver);
        when(invalidMessage.getSender()).thenReturn(sender);
        when(invalidMessage.getContent()).thenReturn("something");

        assertFalse(basicMessageValidation.isValid(invalidMessage));
    }
}
