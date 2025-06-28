package com.example.caoquangnhat_2123110077;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashSet;
import java.util.Set;

public class LibraryManager {

    private static final String PREFS_NAME = "GameLibraryPrefs";
    private static final String KEY_PURCHASED_GAMES = "purchasedGames";

    // Lưu tên của một game vào thư viện
    public static void addGame(Context context, Game game) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        // Lấy danh sách game đã lưu trước đó
        Set<String> purchasedGames = new HashSet<>(prefs.getStringSet(KEY_PURCHASED_GAMES, new HashSet<>()));
        // Thêm game mới vào
        purchasedGames.add(game.getName());
        // Lưu lại danh sách mới
        prefs.edit().putStringSet(KEY_PURCHASED_GAMES, purchasedGames).apply();
    }

    // Lấy danh sách tên các game đã mua
    public static Set<String> getPurchasedGameNames(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return new HashSet<>(prefs.getStringSet(KEY_PURCHASED_GAMES, new HashSet<>()));
    }

    // (Tùy chọn) Kiểm tra xem một game đã có trong thư viện chưa
    public static boolean isGameInLibrary(Context context, Game game) {
        Set<String> purchasedGames = getPurchasedGameNames(context);
        return purchasedGames.contains(game.getName());
    }
}