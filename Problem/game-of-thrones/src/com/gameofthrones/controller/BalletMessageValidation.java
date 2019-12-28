package com.gameofthrones.controller;

import com.gameofthrones.model.Kingdom;
import com.gameofthrones.model.Message;

import java.util.List;

import static java.util.Objects.isNull;

public class BalletMessageValidation extends BasicMessageValidation {
    private List<Kingdom> candidates;

    @Override
    public boolean isValid(Message message) {
        if (isNull(candidates) || candidates.isEmpty()) {
            return false;
        }

        boolean isForElectionNominee = candidates.contains(message.getReceiver());
        return !isForElectionNominee && super.isValid(message);
    }

    public void setCandidates(List<Kingdom> candidates) {
        this.candidates = candidates;
    }
}
