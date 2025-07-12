// File: app/src/main/java/com/example/caoquangnhat_2123110077/CheckoutAdapter.java
package com.example.caoquangnhat_2123110077;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder> {

    private final List<Game> checkoutItems;

    public CheckoutAdapter(List<Game> checkoutItems) {
        this.checkoutItems = checkoutItems;
    }

    @NonNull
    @Override
    public CheckoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkout, parent, false);
        return new CheckoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutViewHolder holder, int position) {
        Game game = checkoutItems.get(position);
        holder.gameName.setText(game.getName());
        holder.gamePrice.setText(game.getPrice());

        // Dùng Glide để tải ảnh game
        Glide.with(holder.itemView.getContext())
                .load(game.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.gameImage);
    }

    @Override
    public int getItemCount() {
        return checkoutItems.size();
    }

    public static class CheckoutViewHolder extends RecyclerView.ViewHolder {
        ImageView gameImage;
        TextView gameName;
        TextView gamePrice;

        public CheckoutViewHolder(@NonNull View itemView) {
            super(itemView);
            gameImage = itemView.findViewById(R.id.checkout_item_image);
            gameName = itemView.findViewById(R.id.checkout_item_name);
            gamePrice = itemView.findViewById(R.id.checkout_item_price);
        }
    }
}