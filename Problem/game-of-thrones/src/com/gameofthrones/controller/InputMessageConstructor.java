package com.gameofthrones.controller;

import com.gameofthrones.controller.helpers.KingdomFactory;
import com.gameofthrones.model.Kingdom;
import com.gameofthrones.model.Message;
import com.gameofthrones.view.IO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InputMessageConstructor implements MessageConstructor{
    private static final String INPUT_MESSAGE = "Input Messages to kingdoms from King Shan\n";
    private static final String RECEIVER_NAME = "Enter receiver name : ";
    private static final String CONTENT = "Enter content : ";
    private static final String STOP_OR_CONTINUE = "Enter: \'done\' to stop\nEnter any key to continue";
    private static final String DONE = "done";
    private List<Message> messages = new ArrayList<>();

    private IO io;

    public InputMessageConstructor(IO io){
        this.io = io;
    }

    public List<Message> constructMessages(Kingdom sender) {
        String option;
        io.display(INPUT_MESSAGE);
        do {
            addMessageFromInput(io, sender);
            io.display(STOP_OR_CONTINUE);
            option = io.getInput().toLowerCase();
        }while (!option.equals(DONE));
        return messages;
    }

    private void addMessageFromInput(IO consoleIO, Kingdom sender) {
        consoleIO.display(RECEIVER_NAME);
        String receiverName = consoleIO.getInput().trim();
        Kingdom receiver = KingdomFactory.kingdomMap.get(receiverName.toLowerCase());
        if(Objects.nonNull(receiver)){
            consoleIO.display(CONTENT);
            Message message = new Message(sender, receiver, consoleIO.getInput());
            messages.add(message);
        }
        else {
            consoleIO.display("Invalid Receiver");
        }
    }
}
