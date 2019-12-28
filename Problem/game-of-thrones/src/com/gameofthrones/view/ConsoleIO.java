package com.gameofthrones.view;


import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

//Represents console user interface
public class ConsoleIO implements IO {

    private PrintStream outputStream;
    private Scanner scanner;

    public ConsoleIO(PrintStream outputStream, InputStream inputStream) {
        this.outputStream = outputStream;
        scanner = new Scanner(inputStream);
    }

    @Override
    public void display(String message) {
        outputStream.println(message);
    }

    @Override
    public String getInput() {
        return scanner.nextLine();
    }
}
