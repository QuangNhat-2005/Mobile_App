// File: app/src/main/java/com/example/caoquangnhat_2123110077/Cart.java
// ĐÃ ĐƯỢC CHỈNH SỬA
package com.example.caoquangnhat_2123110077;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    // 1. Một biến static để lưu trữ thể hiện (instance) duy nhất của lớp Cart
    private static Cart instance;

    // 2. Danh sách các game trong giỏ hàng (bây giờ là một biến của instance)
    private final List<Game> items;

    // 3. Constructor là private để không lớp nào khác có thể tự tạo đối tượng Cart mới
    private Cart() {
        items = new ArrayList<>();
    }

    // 4. Phương thức public static để các lớp khác có thể truy cập vào instance duy nhất
    public static synchronized Cart getInstance() {
        // Nếu instance chưa được tạo...
        if (instance == null) {
            // ...thì tạo mới nó.
            instance = new Cart();
        }
        // Trả về instance đã có hoặc vừa được tạo.
        return instance;
    }

    // --- CÁC PHƯƠNG THỨC KHÁC BÂY GIỜ LÀ PHƯƠNG THỨC CỦA INSTANCE (KHÔNG CÓ STATIC) ---

    public List<Game> getItems() {
        return items;
    }

    public void addItem(Game game) {
        // Kiểm tra để không thêm game trùng lặp
        if (!items.contains(game)) {
            items.add(game);
        }
    }

    public void removeItem(Game game) {
        items.remove(game);
    }

    public void clearCart() {
        items.clear();
    }

    // Lấy danh sách các game đã được chọn để thanh toán
    public List<Game> getSelectedItems() {
        List<Game> selectedItems = new ArrayList<>();
        for (Game game : items) {
            if (game.isSelected()) {
                selectedItems.add(game);
            }
        }
        return selectedItems;
    }

    // === BẮT ĐẦU PHẦN CODE ĐƯỢC THÊM VÀO ===
    /**
     * Kiểm tra xem một game đã có trong giỏ hàng hay chưa.
     * @param game Game cần kiểm tra.
     * @return true nếu game đã có trong giỏ, ngược lại trả về false.
     */
    public boolean isItemInCart(Game game) {
        if (game == null) {
            return false;
        }
        // Sử dụng phương thức .contains() của List.
        // Điều này hoạt động chính xác vì bạn đã định nghĩa phương thức equals() trong lớp Game.
        return items.contains(game);
    }
    // === KẾT THÚC PHẦN CODE ĐƯỢC THÊM VÀO ===
}