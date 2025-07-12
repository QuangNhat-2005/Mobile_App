// File: app/src/main/java/com/example/caoquangnhat_2123110077/DownloadManager.java
package com.example.caoquangnhat_2123110077;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashSet;
import java.util.Set;

public class DownloadManager {

    private static final String PREFS_NAME = "GameDownloadPrefs";
    private static final String KEY_DOWNLOADED_GAMES = "downloaded_game_names";

    /**
     * Đánh dấu một game là đã được tải xuống.
     * @param context Context để truy cập SharedPreferences.
     * @param gameName Tên của game cần đánh dấu.
     * @param isDownloaded Trạng thái tải xuống (true hoặc false).
     */
    public static void setGameDownloaded(Context context, String gameName, boolean isDownloaded) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Lấy danh sách game đã tải hiện tại
        Set<String> downloadedGames = new HashSet<>(prefs.getStringSet(KEY_DOWNLOADED_GAMES, new HashSet<>()));

        if (isDownloaded) {
            // Nếu muốn đánh dấu là đã tải, thêm tên game vào danh sách
            downloadedGames.add(gameName);
        } else {
            // Nếu muốn đánh dấu là chưa tải, xóa tên game khỏi danh sách
            downloadedGames.remove(gameName);
        }

        // Lưu lại danh sách đã cập nhật
        editor.putStringSet(KEY_DOWNLOADED_GAMES, downloadedGames);
        editor.apply();
    }

    /**
     * Kiểm tra xem một game đã được tải xuống hay chưa.
     * @param context Context để truy cập SharedPreferences.
     * @param gameName Tên của game cần kiểm tra.
     * @return true nếu game đã được tải, ngược lại trả về false.
     */
    public static boolean isGameDownloaded(Context context, String gameName) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> downloadedGames = prefs.getStringSet(KEY_DOWNLOADED_GAMES, new HashSet<>());
        return downloadedGames.contains(gameName);
    }
}