package com.gameofthrones.controller;

import com.gameofthrones.view.IO;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gameofthrones.controller.helpers.KingdomFactory.kingdomMap;

public class CandidateRegistry {
    private static final String INPUT_MESSAGE = "Enter the kingdoms competing to be the ruler (names separated space):";
    private static final String INVALID_CANDIDATE_MESSAGES = "Please enter valid candidate names";

    private Set<String> candidateNames;

    public void registerCandidates(IO io) {
        io.display(INPUT_MESSAGE);
        Set<String> candidateNames = getCandidateNamesFromInput(io);

        if (isValidInput(candidateNames)) {
            io.display(INVALID_CANDIDATE_MESSAGES);
            registerCandidates(io);
            return;
        }
        this.candidateNames = candidateNames;
    }

    private boolean isValidInput(Set<String> candidateNames) {
        return !kingdomMap.keySet().containsAll(candidateNames);
    }

    private Set<String> getCandidateNamesFromInput(IO io) {
        String[] names = io.getInput().split(" +");
        return Arrays.stream(names).map(name ->
                name.trim().toLowerCase()).collect(Collectors.toSet());
    }

    public Set<String> getCandidateNames(){
        return candidateNames;
    }

}
