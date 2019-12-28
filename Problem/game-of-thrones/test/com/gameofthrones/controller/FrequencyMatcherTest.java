package com.gameofthrones.controller;

import org.junit.jupiter.api.Test;

import static com.gameofthrones.controller.helpers.FrequencyMatcher.charFrequencyMatch;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FrequencyMatcherTest {

    @Test
    void aNullPatternCanNotBeMatchedWithText() {
        String text = "something";

        assertFalse(charFrequencyMatch(null, text));
    }

    @Test
    void aPatternCanNotBeMatchedWithNullText() {
        String pattern = "something";

        assertFalse(charFrequencyMatch(pattern, null));
    }

    @Test
    void twoSameStringsAreAMatch() {
        String pattern = "something";
        String text = "something";

        assertTrue(charFrequencyMatch(pattern, text));
    }

    @Test
    void mutuallyExclusivePatternAndTextAreNotAMatch() {
        String pattern = "Octopus";
        String text = "Summer is coming";

        assertFalse(charFrequencyMatch(pattern, text));
    }

    @Test
    void aTextWithAtLeastPatternCharFrequencyIsAMatch() {
        String pattern = "Dragon";
        String text = "Drag on Martin!";

        assertTrue(charFrequencyMatch(pattern, text));
    }

    @Test
    void aTextWithoutAtLeastPatternCharFrequencyIsNotAMatch() {
        String pattern = "owll";
        String text = "Let’s swing the sword together";

        assertFalse(charFrequencyMatch(pattern, text));
    }
}
