package com.gameofthrones.controller.actions;

import com.gameofthrones.model.Universe;
import com.gameofthrones.view.IO;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class InvalidActionTest {

    private static final String INVALID_OPTION = "Kindly choose from available options";

    @Test
    void displayInvalidOptionMessageOnIO() {
        InvalidAction invalidAction = new InvalidAction();
        IO consoleIO = mock(IO.class);

        invalidAction.execute(mock(Universe.class), consoleIO);

        verify(consoleIO).display(INVALID_OPTION);
    }
}
