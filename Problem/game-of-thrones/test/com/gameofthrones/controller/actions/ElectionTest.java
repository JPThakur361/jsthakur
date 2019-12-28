package com.gameofthrones.controller.actions;

import com.gameofthrones.controller.BalletMessageValidation;
import com.gameofthrones.controller.CandidateRegistry;
import com.gameofthrones.controller.CountingStation;
import com.gameofthrones.controller.ElectionCoordinator;
import com.gameofthrones.model.Kingdom;
import com.gameofthrones.model.Universe;
import com.gameofthrones.view.IO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.gameofthrones.controller.helpers.KingdomFactory.kingdomMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ElectionTest {
    private static final String NO_VOTERS = "No voters, No Election";

    private ElectionCoordinator electionCoordinator;
    private CandidateRegistry registry;
    private BalletMessageValidation balletMessageValidation;
    private CountingStation countingStation;
    private Universe universe;
    private IO consoleIO;

    private Election election;

    @BeforeEach
    void setUp() {
        electionCoordinator = mock(ElectionCoordinator.class);
        registry = mock(CandidateRegistry.class);
        balletMessageValidation = mock(BalletMessageValidation.class);
        countingStation = mock(CountingStation.class);
        universe = mock(Universe.class);
        consoleIO = mock(IO.class);
        when(countingStation.getQualifiedCandidates()).thenReturn(Collections.singletonList(mock(Kingdom.class)));
        when(countingStation.isNextRoundNeeded()).thenReturn(false);

        election = new Election(electionCoordinator, registry, balletMessageValidation, countingStation);
    }

    @Test
    void doNothingWhenUniverseIsNull() {
        try {
            election.execute(null, consoleIO);
        } catch (NullPointerException exception) {
            fail("Exception thrown");
        }
    }

    @Test
    void doNothingWhenIOIsNull() {
        try {
            election.execute(universe, null);
        } catch (NullPointerException exception) {
            fail("Exception thrown");
        }
    }

    @Test
    void registerCandidatesIntoCandidateRegistry() {
        election.execute(universe, consoleIO);

        verify(registry).registerCandidates(consoleIO);
    }

    @Test
    void addCandidateKingdomsGivenInRegistry() {
        setUpCandidateNames();

        election.execute(universe, consoleIO);

        List<Kingdom> candidates = election.getCandidates();
        assertNotNull(candidates);
        assertEquals(2, candidates.size());
        assertEquals("Air", candidates.get(0).getName());
        assertEquals("Ice", candidates.get(1).getName());
    }

    @Test
    void setCandidatesForMessageValidationStrategy() {
        election.execute(universe, consoleIO);
        List<Kingdom> candidates = election.getCandidates();

        verify(balletMessageValidation).setCandidates(candidates);
    }

    @Test
    void setBalletMessageValidationStrategyForAllKingdoms() {
        election.execute(universe, consoleIO);

        kingdomMap.values().forEach(kingdom -> {
            assertEquals(balletMessageValidation.getClass(), kingdom.getMessageValidationStrategy().getClass());
        });
    }

    @Test
    void setUniverseRulerWhenThereIsOnlyOneCandidate() {
        String candidateName = "air";
        Set<String> candidates = new HashSet<>();
        candidates.add(candidateName);
        when(registry.getCandidateNames()).thenReturn(candidates);
        Kingdom ruler = kingdomMap.get(candidateName);

        election.execute(universe, consoleIO);

        verify(universe).setRuler(ruler);
        assertTrue(ruler.getAllies().isEmpty());
    }

    @Test
    void clearRulerOfTheUniverse() {
        setUpCandidateNames();

        election.execute(universe, consoleIO);

        verify(universe).setRuler(null);
    }

    @Test
    void coordinatorShouldConductTheElectionForCurrentCandidates() {
        setUpCandidateNames();

        election.execute(universe, consoleIO);

        verify(electionCoordinator).conductElection(election.getCandidates());
    }

    @Test
    void setCandidatesToCountingStation() {
        setUpCandidateNames();

        election.execute(universe, consoleIO);

        verify(countingStation).setCandidates(election.getCandidates());
    }

    @Test
    void countingStationShouldDisplayResults() {
        setUpCandidateNames();

        election.execute(universe, consoleIO);

        verify(countingStation).displayResults(1, consoleIO);
    }

    @Test
    void roundShouldBeIncrementedForEveryElectionRound() {
        setUpCandidateNames();
        when(countingStation.isNextRoundNeeded()).thenReturn(true).thenReturn(false);

        election.execute(universe, consoleIO);

        verify(countingStation).displayResults(1, consoleIO);
        verify(countingStation).displayResults(2, consoleIO);
    }

    @Test
    void startElectionWithQualifiedCandidatesInCaseOfTie() {
        setUpCandidateNames();
        when(countingStation.isNextRoundNeeded()).thenReturn(true).thenReturn(false);
        Kingdom qualifier1 = mock(Kingdom.class);
        Kingdom qualifier2 = mock(Kingdom.class);
        List<Kingdom> qualifiers = Arrays.asList(qualifier1, qualifier2);
        when(countingStation.getQualifiedCandidates()).thenReturn(qualifiers);

        election.execute(universe, consoleIO);

        verify(electionCoordinator).conductElection(qualifiers);
        verify(countingStation).setCandidates(qualifiers);
    }

    @Test
    void setRulerOfTheUniverseIfNoFurtherRoundsAreNeeded() {
        setUpCandidateNames();
        when(countingStation.isNextRoundNeeded()).thenReturn(false);
        Kingdom ruler = mock(Kingdom.class);
        when(countingStation.getQualifiedCandidates()).thenReturn(Collections.singletonList(ruler));

        election.execute(universe, consoleIO);

        verify(universe).setRuler(ruler);
    }

    @Test
    void displayNoVotersMessageWhenThereAreSixCandidates() {
        Set<String> candidates = new HashSet<>(Arrays.asList("air", "ice", "fire", "land", "water", "space"));
        when(registry.getCandidateNames()).thenReturn(candidates);

        election.execute(universe, consoleIO);

        verify(consoleIO).display(NO_VOTERS);
    }

    private void setUpCandidateNames() {
        Set<String> candidateNames = new LinkedHashSet<>(Arrays.asList("air", "ice"));
        when(registry.getCandidateNames()).thenReturn(candidateNames);
    }
}
