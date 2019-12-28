package com.gameofthrones;

import com.gameofthrones.controller.gameOfThrones;
import com.gameofthrones.model.Universe;
import com.gameofthrones.view.ConsoleIO;
import com.gameofthrones.view.IO;

import static com.gameofthrones.controller.helpers.ActionFactory.actionMap;
import static com.gameofthrones.controller.helpers.UniverseInitializer.initialize;

public class Main {
    public static void main(String[] args) {
        Universe universe = initialize();
        IO consoleIO = new ConsoleIO(System.out, System.in);
        gameOfThrones gameOfThrones = new gameOfThrones(universe, consoleIO, actionMap);
        gameOfThrones.start();
    }
}
