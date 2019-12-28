package com.gameofthrones.controller.actions;

import com.gameofthrones.model.Universe;
import com.gameofthrones.view.IO;

public interface Action {
    void execute(Universe universe, IO consoleIO);
}
