package com.gameofthrones.controller.helpers;

import com.gameofthrones.controller.InputMessageConstructor;
import com.gameofthrones.model.Kingdom;
import com.gameofthrones.model.Message;
import com.gameofthrones.view.IO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InputMessageConstructorTest {

    private static final String INPUT_MESSAGE = "Input Messages to kingdoms from King Shan\n";
    private static final String RECEIVER_NAME = "Enter receiver name : ";
    private static final String CONTENT = "Enter content : ";
    private static final String STOP_OR_CONTINUE = "Enter: \'done\' to stop\nEnter any key to continue";


    private IO consoleIO;
    private Kingdom sender;
    private InputMessageConstructor messageConstructor;

    @BeforeEach
    void setUp() {
        consoleIO = mock(IO.class);
        sender = mock(Kingdom.class);
        messageConstructor = new InputMessageConstructor(consoleIO);

        when(consoleIO.getInput()).thenReturn("ICE").
                thenReturn("msg").thenReturn("done");
    }

    @Test
    void displayInputMessageAndStopMessage() {
        messageConstructor.constructMessages(sender);

        verify(consoleIO).display(INPUT_MESSAGE);
        verify(consoleIO).display(STOP_OR_CONTINUE);
    }

    @Test
    void displayRecipientAndContentInputMessage() {
        messageConstructor.constructMessages(sender);

        verify(consoleIO).display(RECEIVER_NAME);
        verify(consoleIO).display(CONTENT);
    }

    @Test
    void acceptInputUntilInputOptionIsDone() {
        when(consoleIO.getInput()).thenReturn("Water").thenReturn("message")
                .thenReturn("1").thenReturn("kingdom").
                thenReturn("done").thenReturn("kingdom1");

        messageConstructor.constructMessages(sender);

        verify(consoleIO, times(5)).getInput();
    }

    @Test
    void returnEmptyMessageListIfAllInputsAreInvalid() {
        when(consoleIO.getInput()).thenReturn("invalid").thenReturn("1")
                .thenReturn("invalid1").thenReturn("1").
                thenReturn("invalid2").thenReturn("done");

        List<Message> messages = messageConstructor.constructMessages(sender);

        verify(consoleIO, times(6)).getInput();
        assertNotNull(messages);
        assertTrue(messages.isEmpty());
    }

    @Test
    void returnMessagesReceivedFromInput() {
        when(consoleIO.getInput()).thenReturn("Water").thenReturn("message")
                .thenReturn("1").thenReturn("kingdom").
                thenReturn("done").thenReturn("kingdom1");

        List<Message> messages = messageConstructor.constructMessages(sender);

        assertNotNull(messages);
        assertEquals(1, messages.size());
        assertEquals(sender, messages.get(0).getSender());
        assertEquals("message", messages.get(0).getContent());
        assertEquals("Water", messages.get(0).getReceiver().getName());
    }
}
