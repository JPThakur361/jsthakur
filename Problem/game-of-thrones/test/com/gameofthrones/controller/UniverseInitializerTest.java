package com.gameofthrones.controller;

import com.gameofthrones.controller.helpers.KingdomFactory;
import com.gameofthrones.controller.helpers.UniverseInitializer;
import com.gameofthrones.model.Universe;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UniverseInitializerTest {

    @Test
    void shouldInitializeUniverseWithKingdomsFromKingdomMap() {
        Universe universe = UniverseInitializer.initialize();

        assertTrue(KingdomFactory.kingdomMap.values().containsAll(universe.getKingdoms()));
    }
}
