package com.gameofthrones.view;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


class ConsoleIOTest {
    @Test
    void displaysMessageOnConsole() {
        PrintStream printStream = mock(System.out.getClass());
        InputStream inputStream = mock(System.in.getClass());
        ConsoleIO consoleIO = new ConsoleIO(printStream, inputStream);

        consoleIO.display("Message");

        verify(printStream).println("Message");
    }
}
