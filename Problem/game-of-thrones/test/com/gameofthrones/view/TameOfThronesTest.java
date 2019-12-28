package com.gameofthrones.view;

import com.gameofthrones.controller.gameOfThrones;
import com.gameofthrones.controller.actions.Action;
import com.gameofthrones.controller.actions.Quit;
import com.gameofthrones.model.Universe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class gameOfThronesTest {
    private static final String WELCOME_MESSAGE = "Welcome to game Of Thrones";
    private static final String KNOW_THE_RULER_OPTION = "1. Know the ruler of Southeros";
    private static final String QUIT_OPTION = "Enter quit to exit";
    private static final String OPTION_BELOW = "Enter your option below";
    private static final String AVAILABLE_OPTIONS_HEADER = "-------Available Options-------";

    private gameOfThrones gameOfThrones;
    private Universe universe;
    private IO consoleIO;
    private Map actionMap;
    private Quit quitAction;


    @BeforeEach
    void setUp() {
        universe = mock(Universe.class);
        consoleIO = mock(IO.class);
        quitAction = mock(Quit.class);
        actionMap = mock(Map.class);
        gameOfThrones = new gameOfThrones(universe, consoleIO, actionMap);
        when(consoleIO.getInput()).thenReturn("Quit");
        when(actionMap.get("quit")).thenReturn(quitAction);
    }

    @Test
    void displayWelcomeMessage() {
        gameOfThrones.start();

        verify(consoleIO).display(WELCOME_MESSAGE);
    }

    @Test
    void displayAvailableOptions() {
        gameOfThrones.start();

        verify(consoleIO).display(AVAILABLE_OPTIONS_HEADER);
        verify(consoleIO).display(KNOW_THE_RULER_OPTION);
        verify(consoleIO).display(QUIT_OPTION);
        verify(consoleIO).display(OPTION_BELOW);
    }

    @Test
    void acceptInputUntilQuitAction() {
        when(consoleIO.getInput()).thenReturn("1").thenReturn("quit").thenReturn("2");
        when(actionMap.get("1")).thenReturn(mock(Action.class));

        gameOfThrones.start();

        verify(consoleIO, times(2)).getInput();
    }

    @Test
    void executeActionsBasedOnInput() {
        Action action = mock(Action.class);
        when(consoleIO.getInput()).thenReturn("1").thenReturn("Quit");
        when(actionMap.get("1")).thenReturn(action);

        gameOfThrones.start();

        verify(consoleIO, times(2)).getInput();
        verify(action, times(1)).execute(universe, consoleIO);
        verify(quitAction, times(1)).execute(universe, consoleIO);
    }
}
