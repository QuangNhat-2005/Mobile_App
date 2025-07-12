// File: app/src/main/java/com/example/caoquangnhat_2123110077/CartAdapter.java
package com.example.caoquangnhat_2123110077;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final Context context;
    private final List<Game> cartItems;
    // === THAY ĐỔI: Sử dụng interface đã được tách ra ===
    private final CartInteractionListener listener;

    // === THAY ĐỔI: Sửa tham số của constructor ===
    public CartAdapter(Context context, List<Game> cartItems, CartInteractionListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.listener = listener;
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

        holder.gameName.setText(game.getName());
        holder.gamePrice.setText(game.getPrice());
        holder.selectCheckBox.setChecked(game.isSelected());

        Glide.with(context)
                .load(game.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.gameImage);

        holder.deleteButton.setOnClickListener(v -> listener.onItemDeleted(game, position));
        holder.selectCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isPressed()) {
                listener.onItemSelectionChanged(game, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        CheckBox selectCheckBox;
        ImageView gameImage;
        TextView gameName;
        TextView gamePrice;
        ImageButton deleteButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            selectCheckBox = itemView.findViewById(R.id.checkbox_select);
            gameImage = itemView.findViewById(R.id.cart_item_image);
            gameName = itemView.findViewById(R.id.textview_game_name);
            gamePrice = itemView.findViewById(R.id.textview_game_price);
            deleteButton = itemView.findViewById(R.id.button_delete);
        }
    }
}