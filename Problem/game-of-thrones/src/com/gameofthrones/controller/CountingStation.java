package com.gameofthrones.controller;

import com.gameofthrones.model.Kingdom;
import com.gameofthrones.view.IO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Comparator.comparingInt;
import static java.util.Objects.isNull;

public class CountingStation {

    private static final String ROUND = "\nResults After Ballet Round : ";
    private static final String ALLIE_COUNT = "Allies for %s : %s";
    private static final int ONE = 1;

    private List<Kingdom> candidates;

    public void displayResults(Integer currentRound, IO io) {
        if (isNull(candidates) || candidates.isEmpty()) {
            return;
        }
        if (isNull(io)) {
            return;
        }

        io.display(ROUND + currentRound);
        candidates.forEach(kingdom -> {
            int allyCount = kingdom.getAllies().size();
            io.display(format(ALLIE_COUNT, kingdom.getName(), allyCount));
        });
    }

    public List<Kingdom> getQualifiedCandidates() {
        if (isNull(candidates) || candidates.isEmpty()) {
            return candidates;
        }

        int maxAllyCount = getMaxAllyCount();
        return candidates.stream().filter(kingdom ->
                kingdom.getAllies().size() == maxAllyCount).collect(Collectors.toList());
    }

    private int getMaxAllyCount() {
        Kingdom maxAllyCount = Collections.max(candidates, comparingInt(kingdom -> kingdom.getAllies().size()));
        return maxAllyCount.getAllies().size();
    }

    public void setCandidates(List<Kingdom> candidates) {
        this.candidates = candidates;
    }

    public boolean isNextRoundNeeded() {
        if (isNull(candidates) || candidates.isEmpty()) {
            return false;
        }

        return getQualifiedCandidates().size() > ONE;
    }
}
