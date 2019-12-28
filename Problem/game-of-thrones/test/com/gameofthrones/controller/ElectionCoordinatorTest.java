package com.gameofthrones.controller;

import com.gameofthrones.model.Kingdom;
import com.gameofthrones.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ElectionCoordinatorTest {
    private BalletBox balletBox;
    private ElectionCoordinator electionCoordinator;

    @BeforeEach
    void setUp() {
        balletBox = mock(BalletBox.class);
        electionCoordinator = new ElectionCoordinator(balletBox, 6);
    }

    @Test
    void doNothingIfCandidatesListIsNull() {
        try {
            electionCoordinator.conductElection(null);
        } catch (NullPointerException exception) {
            fail("Exception thrown");
        }
    }

    @Test
    void clearAlliesForAllCandidates() {
        Kingdom candidate1 = mock(Kingdom.class);
        Kingdom candidate2 = mock(Kingdom.class);
        Set allies1 = mock(Set.class);
        Set allies2 = mock(Set.class);
        when(candidate1.getAllies()).thenReturn(allies1);
        when(candidate2.getAllies()).thenReturn(allies2);

        electionCoordinator.conductElection(Arrays.asList(candidate1, candidate2));

        verify(allies1).clear();
        verify(allies2).clear();
    }

    @Test
    void acceptMessagesIntoBalletBox() {
        List<Kingdom> candidates = setUpBasicCandidateData();

        electionCoordinator.conductElection(candidates);

        verify(balletBox).acceptMessages(candidates);
    }

    @Test
    void pickNothingIfBalletBoxIsNull() {
        when(balletBox.getMessages()).thenReturn(null);

        electionCoordinator.conductElection(setUpBasicCandidateData());

        assertNull(electionCoordinator.getRandomMessages());
    }

    @Test
    void pickEmptyListIfBalletIsEmpty() {
        List<Message> emptyBallet = new ArrayList<>();
        when(balletBox.getMessages()).thenReturn(emptyBallet);

        electionCoordinator.conductElection(setUpBasicCandidateData());
        assertEquals(emptyBallet, electionCoordinator.getRandomMessages());
    }

    @Test
    void pickShuffledMessagesFromBallet() {
        Message message = mock(Message.class);
        Message message1 = mock(Message.class);
        Message message2 = mock(Message.class);
        Message message3 = mock(Message.class);
        Kingdom nonNullReceiver = mock(Kingdom.class);
        when(message.getReceiver()).thenReturn(nonNullReceiver);
        when(message1.getReceiver()).thenReturn(nonNullReceiver);
        when(message2.getReceiver()).thenReturn(nonNullReceiver);
        when(message3.getReceiver()).thenReturn(nonNullReceiver);
        List<Message> ballet = Arrays.asList(message, message1, message2, message3);
        List<Message> sameBallet = Arrays.asList(message, message1, message2, message3);
        when(balletBox.getMessages()).thenReturn(ballet).thenReturn(sameBallet);
        int requirement = 4;
        electionCoordinator.setNumberOfMessagesToBePicked(requirement);

        electionCoordinator.conductElection(setUpBasicCandidateData());
        List<Message> firstRandomList = electionCoordinator.getRandomMessages();
        assertEquals(requirement, firstRandomList.size());
        assertTrue(sameBallet.containsAll(firstRandomList));

        electionCoordinator.conductElection(setUpBasicCandidateData());
        List<Message> secondRandomList = electionCoordinator.getRandomMessages();
        assertEquals(requirement, secondRandomList.size());
        assertTrue(sameBallet.containsAll(secondRandomList));

        assertNotEquals(firstRandomList, secondRandomList);
    }

    @Test
    void pickRequiredNumberOfMessagesFromBallet() {
        Message message = mock(Message.class);
        Message message1 = mock(Message.class);
        Message message2 = mock(Message.class);
        Message message3 = mock(Message.class);
        Kingdom nonNullReceiver = mock(Kingdom.class);
        when(message.getReceiver()).thenReturn(nonNullReceiver);
        when(message1.getReceiver()).thenReturn(nonNullReceiver);
        when(message2.getReceiver()).thenReturn(nonNullReceiver);
        when(message3.getReceiver()).thenReturn(nonNullReceiver);
        List<Message> ballet = Arrays.asList(message, message1, message2, message3);
        int requirement = 2;
        when(balletBox.getMessages()).thenReturn(ballet);
        electionCoordinator.setNumberOfMessagesToBePicked(requirement);

        electionCoordinator.conductElection(setUpBasicCandidateData());

        List<Message> messages = electionCoordinator.getRandomMessages();
        assertEquals(requirement, messages.size());
        assertTrue(ballet.containsAll(messages));
    }

    @Test
    void pickWholeBalletWhenRequirementIsGreaterThanBalletSize() {
        ArrayList<Message> ballet = new ArrayList<>();
        Message message = mock(Message.class);
        when(message.getReceiver()).thenReturn(mock(Kingdom.class));
        ballet.add(message);
        when(balletBox.getMessages()).thenReturn(ballet);
        electionCoordinator.setNumberOfMessagesToBePicked(2);

        electionCoordinator.conductElection(setUpBasicCandidateData());

        assertEquals(ballet, electionCoordinator.getRandomMessages());
    }

    @Test
    void doNothingIfToBeDistributedMessageListIsNull() {
        try {
            electionCoordinator.conductElection(setUpBasicCandidateData());
        } catch (NullPointerException exception) {
            fail("Exception thrown");
        }
    }

    @Test
    void distributeMessageToTheirReceivers() {
        Message message = mock(Message.class);
        Kingdom receiver1 = mock(Kingdom.class);
        Kingdom receiver2 = mock(Kingdom.class);
        when(message.getReceiver()).thenReturn(receiver1).thenReturn(receiver2);
        List<Message> messages = Arrays.asList(message, message);
        electionCoordinator.setNumberOfMessagesToBePicked(3);
        when(balletBox.getMessages()).thenReturn(messages);

        electionCoordinator.conductElection(setUpBasicCandidateData());

        verify(receiver1).processAllyInvite(message);
        verify(receiver2).processAllyInvite(message);
    }

    private List<Kingdom> setUpBasicCandidateData() {
        Kingdom kingdom = mock(Kingdom.class);
        Set allies = mock(Set.class);
        when(kingdom.getAllies()).thenReturn(allies);
        return singletonList(kingdom);
    }
}
