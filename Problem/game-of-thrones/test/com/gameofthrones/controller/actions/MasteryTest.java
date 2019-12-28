package com.gameofthrones.controller.actions;

import com.gameofthrones.controller.MessageConstructor;
import com.gameofthrones.controller.MessageValidationStrategy;
import com.gameofthrones.controller.helpers.KingdomFactory;
import com.gameofthrones.model.Kingdom;
import com.gameofthrones.model.Message;
import com.gameofthrones.model.Universe;
import com.gameofthrones.view.IO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MasteryTest {
    private static final String CURRENT_RULER = "Thanks, Current ruler is ";

    private MessageValidationStrategy masteryMessageValidation;
    private MessageConstructor messageConstructor;
    private Kingdom ruleSeeker;
    private IO consoleIO;
    private Universe universe;

    private Mastery mastery;

    @BeforeEach
    void setUp() {
        masteryMessageValidation = mock(MessageValidationStrategy.class);
        messageConstructor = mock(MessageConstructor.class);
        ruleSeeker = mock(Kingdom.class);
        universe = mock(Universe.class);
        consoleIO = mock(IO.class);
        mastery = new Mastery(ruleSeeker, masteryMessageValidation, messageConstructor);
    }

    @Test
    void displayCurrentRulerIfRuleSeekerIsAlreadyTheRuler() {
        when(universe.getRuler()).thenReturn(ruleSeeker);

        mastery.execute(universe, consoleIO);

        verify(consoleIO).display(CURRENT_RULER + ruleSeeker.getName());
        verify(messageConstructor, times(0)).constructMessages(ruleSeeker);
    }

    @Test
    void setMessageValidationStrategyToAllKingdoms() {
        mastery.execute(universe, consoleIO);

        KingdomFactory.kingdomMap.values().forEach(kingdom ->
                assertEquals(masteryMessageValidation, kingdom.getMessageValidationStrategy()));
    }

    @Test
    void ruleSeekerSendsMessagesReceivedFromMessageConstructor() {
        ArrayList<Message> messages = new ArrayList<>();
        when(messageConstructor.constructMessages(ruleSeeker)).thenReturn(messages);

        mastery.execute(universe, consoleIO);

        verify(ruleSeeker).sendMessages(messages);
    }

    @Test
    void ruleSeekerBecomesRulerIfAlliesAreMoreThan3() {
        Kingdom kingdom1 = mock(Kingdom.class);
        Kingdom kingdom2 = mock(Kingdom.class);
        Kingdom kingdom3 = mock(Kingdom.class);
        Set<Kingdom> allies = new HashSet<>(Arrays.asList(kingdom1, kingdom2, kingdom3));
        when(ruleSeeker.getAllies()).thenReturn(allies);

        mastery.execute(universe, consoleIO);

        verify(ruleSeeker).getAllies();
        verify(universe).setRuler(ruleSeeker);
    }

    @Test
    void ruleSeekerBecomesRulerIfAlliesAreNotMoreThan3() {
        Kingdom kingdom3 = mock(Kingdom.class);
        Set<Kingdom> allies = new HashSet<>(Collections.singletonList(kingdom3));
        when(ruleSeeker.getAllies()).thenReturn(allies);

        mastery.execute(universe, consoleIO);

        verify(ruleSeeker).getAllies();
        verify(universe, times(0)).setRuler(ruleSeeker);
    }
}
