package com.gameofthrones.controller;

import com.gameofthrones.controller.actions.Action;
import com.gameofthrones.controller.actions.Quit;
import com.gameofthrones.model.Universe;
import com.gameofthrones.view.IO;

import java.util.Arrays;
import java.util.Map;

public class gameOfThrones {
    private static final String WELCOME_MESSAGE = "Welcome to game Of Thrones";
    private static final String KNOW_THE_RULER_OPTION = "1. Know the ruler of Southeros";
    private static final String MASTERY_OPTION = "2. Help King Shan to become the ruler";
    private static final String ELECTION_OPTION = "3. Help High Priest to find the ruler";
    private static final String QUIT_OPTION = "Enter quit to exit";
    private static final String[] AVAILABLE_OPTIONS = {KNOW_THE_RULER_OPTION, MASTERY_OPTION, ELECTION_OPTION, QUIT_OPTION};
    private static final String AVAILABLE_OPTIONS_HEADER = "-------Available Options-------";
    private static final String OPTION_BELOW = "Enter your option below";

    private Universe universe;
    private IO consoleIO;
    private Map<String, Action> actionMap;

    public gameOfThrones(Universe universe, IO consoleIO, Map<String, Action> actionMap) {
        this.consoleIO = consoleIO;
        this.universe = universe;
        this.actionMap = actionMap;
    }

    public void start() {
        Action action;
        consoleIO.display(WELCOME_MESSAGE);
        do {
            displayAvailableOptions();
            action = actionMap.get(consoleIO.getInput().toLowerCase());
            action.execute(universe, consoleIO);
        } while (!(action instanceof Quit));
    }

    private void displayAvailableOptions() {
        consoleIO.display(AVAILABLE_OPTIONS_HEADER);
        Arrays.stream(AVAILABLE_OPTIONS).forEach(option -> {
            consoleIO.display(option);
        });
        consoleIO.display(OPTION_BELOW);
    }
}
