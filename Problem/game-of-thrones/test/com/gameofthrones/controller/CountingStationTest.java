package com.gameofthrones.controller;

import com.gameofthrones.model.Kingdom;
import com.gameofthrones.view.IO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CountingStationTest {
    private static final String ROUND = "\nResults After Ballet Round : ";
    private static final String ALLIE_COUNT = "Allies for %s : %s";

    private CountingStation countingStation;
    private IO consoleIO;

    @BeforeEach
    void setUp() {
        countingStation = new CountingStation();
        consoleIO = mock(IO.class);
    }

    @Test
    void doNothingIfCandidatesListIsNull() {
        try {
            countingStation.displayResults(1, consoleIO);
        } catch (NullPointerException e) {
            fail("exception thrown");
        }
    }

    @Test
    void doNothingIfCandidatesListIsEmpty() {
        countingStation.setCandidates(new ArrayList<>());
        countingStation.displayResults(1, consoleIO);

        verify(consoleIO, times(0)).display(anyString());
    }

    @Test
    void doNothingIfGivenIOIsNull() {
        try {
            countingStation.setCandidates(Collections.singletonList(mock(Kingdom.class)));
            countingStation.displayResults(1, null);
        } catch (NullPointerException e) {
            fail("exception thrown");
        }
    }

    @Test
    void displayRoundNumberAndCandidatesAllieCount() {
        Kingdom kingdom1 = mock(Kingdom.class);
        Kingdom kingdom2 = mock(Kingdom.class);
        Set allies = mock(LinkedHashSet.class);
        when(allies.size()).thenReturn(2).thenReturn(3);
        when(kingdom1.getName()).thenReturn("Kingdom1");
        when(kingdom2.getName()).thenReturn("Kingdom2");
        when(kingdom1.getAllies()).thenReturn(allies);
        when(kingdom2.getAllies()).thenReturn(allies);

        int currentRound = 1;
        countingStation.setCandidates(Arrays.asList(kingdom1, kingdom2));
        countingStation.displayResults(currentRound, consoleIO);

        verify(consoleIO).display(ROUND + currentRound);
        verify(consoleIO).display(format(ALLIE_COUNT, kingdom1.getName(), 2));
        verify(consoleIO).display(format(ALLIE_COUNT, kingdom2.getName(), 3));
    }

    @Test
    void returnNullWhenCandidateListIsNull() {
        assertNull(countingStation.getQualifiedCandidates());
    }

    @Test
    void returnEmptyListWhenCandidateListIsEmpty() {
        countingStation.setCandidates(new ArrayList<>());
        assertEquals(0, countingStation.getQualifiedCandidates().size());
    }

    @Test
    void returnCandidatesWithMaximumNumberOfAllies() {
        Kingdom kingdom1 = mock(Kingdom.class);
        Kingdom kingdom2 = mock(Kingdom.class);
        Kingdom kingdom3 = mock(Kingdom.class);
        setUpCandidateData(kingdom1, kingdom2, kingdom3);

        countingStation.setCandidates(Arrays.asList(kingdom1, kingdom2, kingdom3));
        List<Kingdom> qualifiedCandidates = countingStation.getQualifiedCandidates();

        assertEquals(2, qualifiedCandidates.size());
        assertTrue(qualifiedCandidates.containsAll(Arrays.asList(kingdom2, kingdom3)));
    }

    @Test
    void nextRoundNotRequiredWhenCandidateListIsNull() {
        assertFalse(countingStation.isNextRoundNeeded());
    }

    @Test
    void nextRoundNotRequiredWhenCandidateListIsEmpty() {
        countingStation.setCandidates(new ArrayList<>());

        assertFalse(countingStation.isNextRoundNeeded());
    }

    @Test
    void nextRoundRequiredWhenQualifiedCandidatesAreMoreThanOne() {
        Kingdom kingdom1 = mock(Kingdom.class);
        Kingdom kingdom2 = mock(Kingdom.class);
        Kingdom kingdom3 = mock(Kingdom.class);
        setUpCandidateData(kingdom1, kingdom2, kingdom3);
        countingStation.setCandidates(Arrays.asList(kingdom1, kingdom2, kingdom3));

        assertTrue(countingStation.isNextRoundNeeded());
    }

    @Test
    void nextRoundNotRequiredWhenQualifiedCandidatesAreNotMoreThanOne() {
        Kingdom kingdom = mock(Kingdom.class);
        Set allies = mock(LinkedHashSet.class);
        when(allies.size()).thenReturn(2);
        when(kingdom.getAllies()).thenReturn(allies);
        countingStation.setCandidates(Collections.singletonList(kingdom));

        assertFalse(countingStation.isNextRoundNeeded());
    }

    private void setUpCandidateData(Kingdom kingdom1, Kingdom kingdom2, Kingdom kingdom3) {
        Set allies1 = mock(LinkedHashSet.class);
        Set allies2 = mock(LinkedHashSet.class);
        Set allies3 = mock(LinkedHashSet.class);
        when(allies1.size()).thenReturn(2);
        when(allies2.size()).thenReturn(4);
        when(allies3.size()).thenReturn(4);
        when(kingdom1.getAllies()).thenReturn(allies1);
        when(kingdom2.getAllies()).thenReturn(allies2);
        when(kingdom3.getAllies()).thenReturn(allies3);
    }
}
