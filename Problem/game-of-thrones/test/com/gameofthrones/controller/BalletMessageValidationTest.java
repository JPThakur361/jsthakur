package com.gameofthrones.controller;

import com.gameofthrones.model.Kingdom;
import com.gameofthrones.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BalletMessageValidationTest {

    private BalletMessageValidation balletMessageValidation;

    @BeforeEach
    void setUp() {
        balletMessageValidation = new BalletMessageValidation();
    }

    @Test
    void balletMessageValidationWithNullElectionNomineesListIsInvalid() {
        assertFalse(balletMessageValidation.isValid(mock(Message.class)));
    }

    @Test
    void balletMessageValidationWithEmptyElectionNomineesListIsInvalid() {
        balletMessageValidation.setCandidates(new ArrayList<>());

        assertFalse(balletMessageValidation.isValid(mock(Message.class)));
    }

    @Test
    void aMessageWithElectionNomineeReceiverAndValidContentIsInvalid() {
        Kingdom sender = mock(Kingdom.class);
        Kingdom receiver = mock(Kingdom.class);
        balletMessageValidation.setCandidates(singletonList(receiver));
        when(receiver.getEmblem()).thenReturn("dragon");
        Message validMessage = mock(Message.class);
        when(validMessage.getReceiver()).thenReturn(receiver);
        when(validMessage.getSender()).thenReturn(sender);
        when(validMessage.getContent()).thenReturn("dralkjgon");

        assertFalse(balletMessageValidation.isValid(validMessage));
    }

    @Test
    void aMessageWithNonElectionNomineeReceiverAndValidContentIsValid() {
        Kingdom sender = mock(Kingdom.class);
        Kingdom receiver = mock(Kingdom.class);
        balletMessageValidation.setCandidates(singletonList(sender));
        when(receiver.getEmblem()).thenReturn("dragon");
        Message validMessage = mock(Message.class);
        when(validMessage.getReceiver()).thenReturn(receiver);
        when(validMessage.getSender()).thenReturn(sender);
        when(validMessage.getContent()).thenReturn("dralkjgon");

        assertTrue(balletMessageValidation.isValid(validMessage));
    }

    @Test
    void aMessageWithNonElectionNomineeReceiverAndInvalidContentIsInvalid() {
        Kingdom sender = mock(Kingdom.class);
        Kingdom receiver = mock(Kingdom.class);
        balletMessageValidation.setCandidates(singletonList(sender));
        when(receiver.getEmblem()).thenReturn("owl");
        Message invalidMessage = mock(Message.class);
        when(invalidMessage.getReceiver()).thenReturn(receiver);
        when(invalidMessage.getSender()).thenReturn(sender);
        when(invalidMessage.getContent()).thenReturn("something");

        assertFalse(balletMessageValidation.isValid(invalidMessage));
    }
}
