package com.gameofthrones.controller.actions;

import com.gameofthrones.model.Universe;
import com.gameofthrones.view.IO;

public class Quit implements Action {

    private static final String THANK_YOU = "Thanks for your time";

    @Override
    public void execute(Universe universe, IO consoleIO) {
        consoleIO.display(THANK_YOU);
    }
}
