package com.gameofthrones.controller.helpers;

import com.gameofthrones.model.Kingdom;
import com.gameofthrones.model.Universe;

import java.util.ArrayList;

import static com.gameofthrones.controller.helpers.KingdomFactory.kingdomMap;

public class UniverseInitializer {

    public static Universe initialize(){
        ArrayList<Kingdom> kingdoms = new ArrayList<>(kingdomMap.values());
        return new Universe(kingdoms);
    }
}
