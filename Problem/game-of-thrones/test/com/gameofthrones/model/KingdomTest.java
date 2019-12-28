package com.gameofthrones.model;

import com.gameofthrones.controller.MessageValidationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class KingdomTest {

    private Kingdom kingdom;

    @BeforeEach
    void setUp() {
        kingdom = new Kingdom("name", "emblem");
    }

    @Test
    void aKingdomShouldNotBeEqualToNull() {
        assertNotEquals(kingdom, null);
    }

    @Test
    void aKingdomShouldBeEqualToItself() {
        assertEquals(kingdom, kingdom);
    }

    @Test
    void aKingdomShouldNotBeEqualToStringKingdom() {
        assertNotEquals("Kingdom", kingdom);
    }

    @Test
    void twoKingdomsWithSameNameAndEmblemShouldBeEqual() {
        Kingdom otherKingdom = new Kingdom("name", "emblem");

        assertEquals(kingdom, otherKingdom);
    }

    @Test
    void twoKingdomsWithSameNameAndDifferentEmblemsShouldNotBeEqual() {
        Kingdom otherKingdom = new Kingdom("name", "emblem1");

        assertNotEquals(kingdom, otherKingdom);
    }

    @Test
    void twoKingdomsWithSameEmblemAndDifferentNamesShouldNotBeEqual() {
        Kingdom otherKingdom = new Kingdom("name1", "emblem");

        assertNotEquals(kingdom, otherKingdom);
    }

    @Test
    void twoKingdomsWithDifferentNamesAndDifferentEmblemsShouldNotBeEqual() {
        Kingdom otherKingdom = new Kingdom("name1", "emblem1");

        assertNotEquals(kingdom, otherKingdom);
    }

    @Test
    void twoKingdomsWithSameNameAndSameEmblemShouldHaveSameHash() {
        Kingdom otherKingdom = new Kingdom("name", "emblem");

        assertEquals(kingdom.hashCode(), otherKingdom.hashCode());
    }

    @Test
    void twoKingdomsWithSameNameAndDifferentEmblemsShouldNotHaveSameHash() {
        Kingdom otherKingdom = new Kingdom("name", "emblem1");

        assertNotEquals(kingdom.hashCode(), otherKingdom.hashCode());
    }

    @Test
    void twoKingdomsWithDifferentNamesAndSameEmblemShouldNotHaveSameHash() {
        Kingdom otherKingdom = new Kingdom("name1", "emblem");

        assertNotEquals(kingdom.hashCode(), otherKingdom.hashCode());
    }

    @Test
    void twoKingdomsWithDifferentNamesAndDifferentEmblemsShouldNotHaveSameHash() {
        Kingdom otherKingdom = new Kingdom("name1", "emblem1");

        assertNotEquals(kingdom.hashCode(), otherKingdom.hashCode());
    }

    @Test
    void aNullObjectCanNeverBecomeAnAlly() {
        Kingdom nullKingdom = null;

        kingdom.joinAllies(nullKingdom);

        assertEquals(0, kingdom.getAllies().size());
    }

    @Test
    void sameKingdomCanNeverBecomeAnAllyToItself() {
        kingdom.joinAllies(kingdom);

        assertEquals(0, kingdom.getAllies().size());
    }

    @Test
    void otherKingdomCanChooseToBecomeAnAlly() {
        Kingdom otherKingdom = new Kingdom("name1", "emblem1");

        kingdom.joinAllies(otherKingdom);

        assertTrue(kingdom.getAllies().contains(otherKingdom));
    }

    @Test
    void noDuplicatesAlliesShouldBeAdded() {
        Kingdom otherKingdom = new Kingdom("name1", "emblem1");

        kingdom.joinAllies(otherKingdom);
        kingdom.joinAllies(otherKingdom);

        assertNotEquals(2, kingdom.getAllies().size());
    }

    @Test
    void kingdomsWithSameNameAndEmblemShouldBeTreatedAsSingleAlly() {
        Kingdom kingdom1 = new Kingdom("name1", "emblem1");
        Kingdom kingdom2 = new Kingdom("name1", "emblem1");

        kingdom.joinAllies(kingdom1);
        kingdom.joinAllies(kingdom2);

        assertNotEquals(2, kingdom.getAllies().size());
    }

    @Test
    void doNothingOnNullMessageValidationStrategy() {
        Message message = mock(Message.class);
        Kingdom sender = mock(Kingdom.class);
        when(message.getSender()).thenReturn(sender);

        kingdom.processAllyInvite(message);

        verify(sender, times(0)).joinAllies(kingdom);
    }

    @Test
    void becomeAllyOfValidInviteSender() {
        Message message = mock(Message.class);
        Kingdom sender = mock(Kingdom.class);
        MessageValidationStrategy messageValidation = mock(MessageValidationStrategy.class);
        when(message.getSender()).thenReturn(sender);
        when(messageValidation.isValid(message)).thenReturn(true);
        kingdom.setMessageValidationStrategy(messageValidation);

        kingdom.processAllyInvite(message);

        verify(messageValidation, times(1)).isValid(message);
        verify(sender, times(1)).joinAllies(kingdom);
    }

    @Test
    void doNothingOnInvalidAllyInvite() {
        Message message = mock(Message.class);
        Kingdom sender = mock(Kingdom.class);
        when(message.getSender()).thenReturn(sender);
        MessageValidationStrategy messageValidation = mock(MessageValidationStrategy.class);
        when(messageValidation.isValid(message)).thenReturn(false);
        kingdom.setMessageValidationStrategy(messageValidation);

        kingdom.processAllyInvite(message);

        verify(messageValidation, times(1)).isValid(message);
        verify(sender, times(0)).joinAllies(kingdom);
    }

    @Test
    void doNothingWhenMessageListIsNull() {
        try {
            kingdom.sendMessages(null);
        } catch (NullPointerException exception) {
            fail("exception thrown");
        }
    }

    @Test
    void allReceiversOfMessagesShouldBeInvited() {
        Kingdom receiver1 = mock(Kingdom.class);
        Kingdom receiver2 = mock(Kingdom.class);
        Message message1 = mock(Message.class);
        Message message2 = mock(Message.class);
        when(message1.getReceiver()).thenReturn(receiver1);
        when(message2.getReceiver()).thenReturn(receiver2);
        List<Message> messages = Arrays.asList(message1, message2);

        kingdom.sendMessages(messages);

        verify(receiver1).processAllyInvite(message1);
        verify(receiver2).processAllyInvite(message2);
    }
}
