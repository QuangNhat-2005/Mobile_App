// File: app/src/main/java/com/example/caoquangnhat_2123110077/GameRepository.java
package com.example.caoquangnhat_2123110077;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameRepository {

    private static List<Game> allGamesCache;

    public static List<Game> getAllGames() {
        if (allGamesCache == null) {
            allGamesCache = new ArrayList<>();

        }
        return allGamesCache;
    }

}