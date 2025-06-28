package com.example.caoquangnhat_2123110077;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Game> cartItems;
    private Context context;
    private OnCartItemChangeListener changeListener;

    // Interface để thông báo cho CartActivity khi có thay đổi (chọn/bỏ chọn, xóa)
    public interface OnCartItemChangeListener {
        void onItemRemoved(Game game);
        void onSelectionChanged(); // Gọi khi có sự thay đổi lựa chọn
    }

    public CartAdapter(Context context, List<Game> cartItems, OnCartItemChangeListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.changeListener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Game game = cartItems.get(position);

        // Hiển thị dữ liệu
        holder.textViewGameName.setText(game.getName());
        holder.textViewGamePrice.setText(game.getPrice());
        if (game.getImageResourceIds() != null && !game.getImageResourceIds().isEmpty()) {
            holder.imageViewGame.setImageResource(game.getImageResourceIds().get(0));
        }

        // --- LOGIC MỚI CHO CHECKBOX VÀ CLICK ---

        // 1. Đặt trạng thái cho CheckBox
        holder.checkBox.setChecked(game.isSelected());

        // 2. Xử lý sự kiện khi nhấn vào CheckBox
        holder.checkBox.setOnClickListener(v -> {
            boolean isChecked = holder.checkBox.isChecked();
            game.setSelected(isChecked);
            // Thông báo cho Activity biết để tính lại tổng tiền
            if (changeListener != null) {
                changeListener.onSelectionChanged();
            }
        });

        // 3. Xử lý sự kiện khi nhấn vào nút xóa
        holder.imageViewRemove.setOnClickListener(v -> {
            if (changeListener != null) {
                changeListener.onItemRemoved(game);
            }
        });

        // 4. Xử lý sự kiện khi nhấn vào toàn bộ item (trừ nút xóa và checkbox)
        // để chuyển sang trang chi tiết
        holder.itemLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, GameDetailActivity.class);
            // Vì Game đã implements Serializable, ta có thể gửi cả đối tượng
            intent.putExtra("GAME_OBJECT", game);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewGame, imageViewRemove;
        TextView textViewGameName, textViewGamePrice;
        CheckBox checkBox; // Thêm CheckBox
        LinearLayout itemLayout; // Layout chính của item

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewGame = itemView.findViewById(R.id.imageViewGameCart);
            textViewGameName = itemView.findViewById(R.id.textViewGameNameCart);
            textViewGamePrice = itemView.findViewById(R.id.textViewGamePriceCart);
            imageViewRemove = itemView.findViewById(R.id.imageViewRemoveItem);
            checkBox = itemView.findViewById(R.id.checkBoxItem); // Ánh xạ CheckBox
            itemLayout = itemView.findViewById(R.id.layout_item_cart); // Ánh xạ layout chính
        }
    }
}