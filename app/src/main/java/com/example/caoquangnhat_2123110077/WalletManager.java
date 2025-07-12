// File: app/src/main/java/com/example/caoquangnhat_2123110077/WalletManager.java
// PHIÊN BẢN MỚI DÙNG SHARED PREFERENCES
package com.example.caoquangnhat_2123110077;

import android.content.Context;
import android.content.SharedPreferences;

public class WalletManager {

    private static final String PREFS_NAME = "WalletPrefs";
    private static final String KEY_WALLET_BALANCE = "wallet_balance_"; // Thêm dấu gạch dưới để nối với userId
    private static final float INITIAL_BALANCE = 500000f; // Quà chào mừng 500,000đ

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // Tạo ví cho người dùng mới
    public static void createWalletForNewUser(Context context, String userId) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        // Mỗi người dùng sẽ có một key riêng, ví dụ: "wallet_balance_user123"
        editor.putFloat(KEY_WALLET_BALANCE + userId, INITIAL_BALANCE);
        editor.apply();
    }

    // Lấy số dư hiện tại
    public static float getWalletBalance(Context context, String userId) {
        // Nếu không tìm thấy, mặc định trả về 0
        return getPrefs(context).getFloat(KEY_WALLET_BALANCE + userId, 0f);
    }

    // Cập nhật số dư
    public static void updateBalance(Context context, String userId, float newBalance) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putFloat(KEY_WALLET_BALANCE + userId, newBalance);
        editor.apply();
    }
}