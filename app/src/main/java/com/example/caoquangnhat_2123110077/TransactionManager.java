// File: app/src/main/java/com/example/caoquangnhat_2123110077/TransactionManager.java
// FILE MỚI
package com.example.caoquangnhat_2123110077;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TransactionManager {
    private static final String PREFS_NAME = "TransactionPrefs";
    private static final String KEY_TRANSACTIONS = "transactions";
    private static final Gson gson = new Gson();

    // Lưu một giao dịch mới
    public static void saveTransaction(Context context, Transaction transaction) {
        List<Transaction> transactions = getTransactions(context);
        transactions.add(0, transaction); // Thêm vào đầu danh sách để giao dịch mới nhất hiện lên trước

        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        String json = gson.toJson(transactions);
        editor.putString(KEY_TRANSACTIONS, json);
        editor.apply();
    }

    // Lấy danh sách tất cả giao dịch
    public static List<Transaction> getTransactions(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_TRANSACTIONS, null);
        if (json == null) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<ArrayList<Transaction>>() {}.getType();
        return gson.fromJson(json, type);
    }
}