package com.example.caoquangnhat_2123110077;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    public static final List<Game> items = new ArrayList<>();

    // --- CẬP NHẬT PHƯƠNG THỨC NÀY ---
    // Trả về true nếu thêm thành công, false nếu đã tồn tại
    public static boolean addItem(Game game) {
        // Kiểm tra xem game đã có trong giỏ hàng chưa
        // .contains() hoạt động đúng nhờ phương thức equals() trong Game.java
        if (items.contains(game)) {
            return false; // Báo hiệu rằng game đã tồn tại, không thêm nữa
        } else {
            items.add(game);
            return true; // Báo hiệu đã thêm thành công
        }
    }

    public static void removeItem(Game game) {
        items.remove(game);
    }

    public static List<Game> getItems() {
        return items;
    }

    public static int getCartSize() {
        return items.size();
    }

    public static void clearCart() {
        items.clear();
    }
}