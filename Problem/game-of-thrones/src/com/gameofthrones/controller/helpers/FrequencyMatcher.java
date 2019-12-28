package com.gameofthrones.controller.helpers;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.isNull;

public class FrequencyMatcher {

    public static boolean charFrequencyMatch(String pattern, String text) {
        if (Objects.equals(pattern, text)) {
            return true;
        }
        if (isNull(pattern) || isNull(text)) {
            return false;
        }
        return atLeastFrequencyMatch(pattern, text);
    }

    private static boolean atLeastFrequencyMatch(String pattern, String text) {
        Map<Character, Integer> patternFrequencyMap = getFrequencyMap(pattern);
        Map<Character, Integer> textFrequencyMap = getFrequencyMap(text);
        if (!textFrequencyMap.keySet().containsAll(patternFrequencyMap.keySet())) {
            return false;
        }
        for (Character character : patternFrequencyMap.keySet()) {
            if (textFrequencyMap.get(character) < patternFrequencyMap.get(character)) {
                return false;
            }
        }
        return true;
    }

    private static Map<Character, Integer> getFrequencyMap(String string) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        string.toLowerCase().chars().mapToObj(c -> (char) c).forEach(character -> {
            Integer frequency = frequencyMap.get(character);
            frequency = isNull(frequency) ? 0 : frequency;
            frequencyMap.put(character, ++frequency);
        });
        return frequencyMap;
    }
}
