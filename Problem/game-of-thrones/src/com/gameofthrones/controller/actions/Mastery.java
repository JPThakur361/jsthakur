package com.gameofthrones.controller.actions;

import com.gameofthrones.controller.MessageConstructor;
import com.gameofthrones.controller.MessageValidationStrategy;
import com.gameofthrones.model.Kingdom;
import com.gameofthrones.model.Message;
import com.gameofthrones.model.Universe;
import com.gameofthrones.view.IO;

import java.util.List;

import static com.gameofthrones.controller.helpers.KingdomFactory.kingdomMap;

public class Mastery implements Action {

    private static final int REQUIRED_NUMBER_OF_ALLIES = 3;
    private static final String CURRENT_RULER = "Thanks, Current ruler is ";
    private Kingdom ruleSeeker;
    private MessageValidationStrategy masteryMessageValidation;
    private MessageConstructor messageConstructor;

    public Mastery(Kingdom ruleSeeker, MessageValidationStrategy masteryMessageValidation,
                   MessageConstructor messageConstructor) {
        this.ruleSeeker = ruleSeeker;
        this.masteryMessageValidation = masteryMessageValidation;
        this.messageConstructor = messageConstructor;
    }

    @Override
    public void execute(Universe universe, IO consoleIO) {
        if(ruleSeeker.equals(universe.getRuler())){
            consoleIO.display(CURRENT_RULER + ruleSeeker.getName());
            return;
        }

        setMessageValidationStrategy(masteryMessageValidation);
        List<Message> messages = messageConstructor.constructMessages(ruleSeeker);
        ruleSeeker.sendMessages(messages);
        setRuler(universe);
    }

    private void setRuler(Universe universe) {
        if (ruleSeeker.getAllies().size() >= REQUIRED_NUMBER_OF_ALLIES) {
            universe.setRuler(ruleSeeker);
        }
    }

    private void setMessageValidationStrategy(MessageValidationStrategy masteryMessageValidation) {
        kingdomMap.values().forEach(kingdom -> {
            kingdom.setMessageValidationStrategy(masteryMessageValidation);
        });
    }
}
