package com.example.caoquangnhat_2123110077;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashSet;
import java.util.Set;

public class DownloadManager {

    private static final String PREFS_NAME = "GameDownloadPrefs";
    private static final String KEY_DOWNLOADED_GAMES = "downloadedGames";

    // Thêm game vào danh sách đã tải
    public static void addDownloadedGame(Context context, Game game) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> downloadedGames = new HashSet<>(prefs.getStringSet(KEY_DOWNLOADED_GAMES, new HashSet<>()));
        downloadedGames.add(game.getName());
        prefs.edit().putStringSet(KEY_DOWNLOADED_GAMES, downloadedGames).apply();
    }

    // Kiểm tra xem game đã được tải chưa
    public static boolean isGameDownloaded(Context context, Game game) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> downloadedGames = prefs.getStringSet(KEY_DOWNLOADED_GAMES, new HashSet<>());
        return downloadedGames.contains(game.getName());
    }
}