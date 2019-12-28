package com.gameofthrones.controller.helpers;

import com.gameofthrones.controller.actions.Election;
import com.gameofthrones.controller.actions.InvalidAction;
import com.gameofthrones.controller.actions.KnowRuler;
import com.gameofthrones.controller.actions.Mastery;
import com.gameofthrones.controller.actions.Quit;
import org.junit.jupiter.api.Test;

import static com.gameofthrones.controller.helpers.ActionFactory.actionMap;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ActionFactoryTest {

    @Test
    void returnKnowTheRulerActionForInputOne() {
        assertTrue(actionMap.get("1") instanceof KnowRuler);
    }

    @Test
    void returnMasteryActionForInputTwo() {
        assertTrue(actionMap.get("2") instanceof Mastery);
    }

    @Test
    void returnElectionActionForInputTwo() {
        assertTrue(actionMap.get("3") instanceof Election);
    }

    @Test
    void returnKnowQuitActionForInputQuit() {
        assertTrue(actionMap.get("quit") instanceof Quit);
    }

    @Test
    void returnKnowInvalidActionIfInputNotFoundInMap() {
        assertTrue(actionMap.get("something invalid") instanceof InvalidAction);
    }
}
