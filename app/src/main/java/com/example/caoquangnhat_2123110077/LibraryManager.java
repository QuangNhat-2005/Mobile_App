// File: app/src/main/java/com/example/caoquangnhat_2123110077/LibraryManager.java
package com.example.caoquangnhat_2123110077;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LibraryManager {

    private static final String PREFS_NAME = "GameLibraryPrefs";
    // Key mới để lưu danh sách game dưới dạng JSON
    private static final String KEY_LIBRARY_GAMES_JSON = "library_games_json";

    // Phương thức này không còn cần thiết nữa vì ta sẽ lưu cả đối tượng
    // public static void addGameToLibrary(Context context, Game game) { ... }

    /**
     * Thêm một game vào thư viện.
     * Phương thức này sẽ chuyển đổi đối tượng Game thành JSON và lưu lại.
     * @param context Context để truy cập SharedPreferences.
     * @param gameToAdd Game cần thêm.
     */
    public static void addGameToLibrary(Context context, Game gameToAdd) {
        // Lấy danh sách game hiện tại trong thư viện
        List<Game> libraryGames = getLibraryGames(context);

        // Kiểm tra xem game đã tồn tại chưa để tránh trùng lặp
        if (!libraryGames.contains(gameToAdd)) {
            libraryGames.add(gameToAdd);
        }

        // Lưu lại toàn bộ danh sách đã được cập nhật
        saveLibraryGames(context, libraryGames);
    }

    /**
     * Lấy danh sách các đối tượng Game từ thư viện.
     * Phương thức này sẽ đọc chuỗi JSON và chuyển đổi ngược lại thành List<Game>.
     * @param context Context để truy cập SharedPreferences.
     * @return Một List<Game> chứa các game trong thư viện.
     */
    public static List<Game> getLibraryGames(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_LIBRARY_GAMES_JSON, null);

        if (json == null) {
            return new ArrayList<>(); // Trả về danh sách rỗng nếu chưa có gì
        }

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Game>>() {}.getType();
        return gson.fromJson(json, type);
    }

    /**
     * Phương thức nội bộ để lưu danh sách game vào SharedPreferences.
     * @param context Context để truy cập SharedPreferences.
     * @param games Danh sách game cần lưu.
     */
    private static void saveLibraryGames(Context context, List<Game> games) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(games);
        editor.putString(KEY_LIBRARY_GAMES_JSON, json);
        editor.apply();
    }

    /**
     * Lấy tên của các game đã mua (dùng cho HomeActivity để kiểm tra)
     * @param context Context để truy cập SharedPreferences.
     * @return Một Set<String> chứa tên các game.
     */
    public static Set<String> getPurchasedGameNames(Context context) {
        List<Game> libraryGames = getLibraryGames(context);
        Set<String> gameNames = new HashSet<>();
        for (Game game : libraryGames) {
            gameNames.add(game.getName());
        }
        return gameNames;
    }
}