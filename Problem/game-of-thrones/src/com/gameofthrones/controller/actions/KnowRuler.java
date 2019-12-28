package com.gameofthrones.controller.actions;

import com.gameofthrones.model.Kingdom;
import com.gameofthrones.model.Universe;
import com.gameofthrones.view.IO;

import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class KnowRuler implements Action {

    private static final String RULER_NAME = "Ruler Name : ";
    private static final String ALLIES_OF_RULER = "Allies of Ruler : ";
    private static final String NONE = "None";
    private static final String KING = " / King: ";

    @Override
    public void execute(Universe universe, IO consoleIO) {
        if (isNull(universe) || isNull(consoleIO)) {
            return;
        }
        Kingdom ruler = universe.getRuler();
        if (isNull(ruler)) {
            displayRulerInfo(consoleIO, NONE, NONE);
            return;
        }
        displayRulerInfo(consoleIO, getRulerName(ruler), getAlliesOfRuler(ruler));
    }

    private String getRulerName(Kingdom rulerKingdom) {
        String rulerName = rulerKingdom.getName();
        if (nonNull(rulerKingdom.getRuler())) {
            rulerName += KING + rulerKingdom.getRuler().getName();
        }
        return rulerName;
    }

    private void displayRulerInfo(IO consoleIO, String name, String allies) {
        consoleIO.display(RULER_NAME + name);
        consoleIO.display(ALLIES_OF_RULER + allies);
    }

    private String getAlliesOfRuler(Kingdom ruler) {
        if (ruler.getAllies().isEmpty()) {
            return NONE;
        }
        return ruler.getAllies().stream().map(Kingdom::getName)
                .collect(Collectors.joining(", "));
    }
}
