package com.gameofthrones.controller;

import com.gameofthrones.model.Kingdom;
import com.gameofthrones.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BalletBoxTest {
    private MessageConstructor messageConstructor;
    private BalletBox balletBox;

    @BeforeEach
    void setUp() {
        messageConstructor = mock(MessageConstructor.class);
        balletBox = new BalletBox(messageConstructor);
    }

    @Test
    void doNothingIfCandidateListIsNull() {
        try {
            balletBox.acceptMessages(null);
        } catch (NullPointerException exception) {
            fail("Exception thrown");
        }
    }

    @Test
    void getConstructMessagesForAllCandidates() {
        Kingdom airKingdom = mock(Kingdom.class);
        Kingdom iceKingdom = mock(Kingdom.class);

        balletBox.acceptMessages(Arrays.asList(airKingdom, iceKingdom));

        verify(messageConstructor).constructMessages(airKingdom);
        verify(messageConstructor).constructMessages(iceKingdom);
    }

    @Test
    void addAllMessagesFromConstructorToBalletBox() {
        Kingdom airKingdom = mock(Kingdom.class);
        Kingdom iceKingdom = mock(Kingdom.class);
        Message airMessage = mock(Message.class);
        Message iceMessage = mock(Message.class);
        when(messageConstructor.constructMessages(airKingdom)).thenReturn(singletonList(airMessage));
        when(messageConstructor.constructMessages(iceKingdom)).thenReturn(singletonList(iceMessage));

        balletBox.acceptMessages(Arrays.asList(airKingdom, iceKingdom));

        List<Message> messages = balletBox.getMessages();
        assertNotNull(messages);
        assertEquals(2, messages.size());
        assertTrue(messages.containsAll(Arrays.asList(airMessage, iceMessage)));
    }
}
