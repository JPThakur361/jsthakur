package com.gameofthrones.controller.helpers;

import com.gameofthrones.model.King;
import com.gameofthrones.model.Kingdom;

import java.util.HashMap;
import java.util.Map;

public class KingdomFactory {
    private static final String LAND = "Land";
    private static final String WATER = "Water";
    private static final String ICE = "Ice";
    private static final String AIR = "Air";
    private static final String FIRE = "Fire";
    private static final String SPACE = "Space";

    private static final String PANDA = "Panda";
    private static final String OCTOPUS = "Octopus";
    private static final String MAMMOTH = "Mammoth";
    private static final String OWL = "Owl";
    private static final String DRAGON = "Dragon";
    private static final String GORILLA = "Gorilla";

    private static final King KING_SHAN = new King("Shan");

    private static final Kingdom LAND_KINGDOM = new Kingdom(LAND, PANDA);
    private static final Kingdom WATER_KINGDOM = new Kingdom(WATER, OCTOPUS);
    private static final Kingdom ICE_KINGDOM = new Kingdom(ICE, MAMMOTH);
    private static final Kingdom AIR_KINGDOM = new Kingdom(AIR, OWL);
    private static final Kingdom FIRE_KINGDOM = new Kingdom(FIRE, DRAGON);
    private static final Kingdom SPACE_KINGDOM = new Kingdom(SPACE, GORILLA);

    public static Map<String, Kingdom> kingdomMap = new HashMap<String, Kingdom>() {{
            put(LAND.toLowerCase(), LAND_KINGDOM);
            put(WATER.toLowerCase(), WATER_KINGDOM);
            put(ICE.toLowerCase(), ICE_KINGDOM);
            put(AIR.toLowerCase(), AIR_KINGDOM);
            put(FIRE.toLowerCase(), FIRE_KINGDOM);
            SPACE_KINGDOM.setRuler(KING_SHAN);
            put(SPACE.toLowerCase(), SPACE_KINGDOM);
        }
    };
}
