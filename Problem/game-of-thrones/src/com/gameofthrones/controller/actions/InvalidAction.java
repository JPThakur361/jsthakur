package com.gameofthrones.controller.actions;

import com.gameofthrones.model.Universe;
import com.gameofthrones.view.IO;

public class InvalidAction implements Action {

    private static final String INVALID_OPTION = "Kindly choose from available options";

    @Override
    public void execute(Universe universe, IO consoleIO) {
        consoleIO.display(INVALID_OPTION);
    }
}
