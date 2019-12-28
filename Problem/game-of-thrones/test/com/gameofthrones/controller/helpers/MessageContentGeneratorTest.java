package com.gameofthrones.controller.helpers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class MessageContentGeneratorTest {

    private MessageContentGenerator messageContentGenerator;
    private Random random;

    @BeforeEach
    void setUp() {
        random = mock(Random.class);
        messageContentGenerator = new MessageContentGenerator(random);
    }

    @Test
    void returnRandomMessageFromAvailableList() {
        when(random.nextInt(anyInt())).thenReturn(1);

        String expected = "a1d22n333a4444p";
        assertEquals(expected, messageContentGenerator.getRandomMessageContent());
    }
}
