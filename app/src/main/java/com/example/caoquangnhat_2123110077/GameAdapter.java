// File: app/src/main/java/com/example/caoquangnhat_2123110077/GameAdapter.java
// ĐÃ ĐƯỢC CHỈNH SỬA
package com.example.caoquangnhat_2123110077;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    private Context context;
    private List<Game> gameList;
    private Set<String> ownedGameNames;

    public GameAdapter(Context context, List<Game> gameList, Set<String> ownedGameNames) {
        this.context = context;
        this.gameList = gameList;
        this.ownedGameNames = ownedGameNames;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_game, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game game = gameList.get(position);

        holder.textViewName.setText(game.getName());

        // === BẮT ĐẦU LOGIC HIỂN THỊ GIÁ MỚI ===
        if (game.isOnSale()) {
            // Nếu game đang giảm giá
            holder.textViewPrice.setText(game.getPrice()); // Hiển thị giá mới
            holder.textViewPrice.setTextColor(context.getResources().getColor(R.color.sale_price_color)); // Màu xanh lá cho nổi bật

            holder.textViewOriginalPrice.setVisibility(View.VISIBLE); // Hiện giá gốc
            holder.textViewOriginalPrice.setText(game.getOriginalPrice()); // Đặt giá trị
            // Thêm cờ gạch ngang cho giá gốc
            holder.textViewOriginalPrice.setPaintFlags(holder.textViewOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            // Nếu game không giảm giá
            holder.textViewPrice.setText(game.getPrice());
            holder.textViewPrice.setTextColor(context.getResources().getColor(R.color.purple_500)); // Màu mặc định

            holder.textViewOriginalPrice.setVisibility(View.GONE); // Ẩn giá gốc đi
        }
        // === KẾT THÚC LOGIC HIỂN THỊ GIÁ MỚI ===

        String imageUrl = game.getImageUrl();
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imageViewGame);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, GameDetailActivity.class);
            intent.putExtra("GAME_OBJECT", game);
            boolean isOwned = ownedGameNames != null && ownedGameNames.contains(game.getName());
            intent.putExtra("IS_OWNED", isOwned);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public void updateOwnedGames(Set<String> newOwnedGameNames) {
        this.ownedGameNames = newOwnedGameNames;
        notifyDataSetChanged();
    }

    public void filterList(List<Game> filteredList) {
        this.gameList = filteredList;
        notifyDataSetChanged();
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewGame;
        TextView textViewName;
        TextView textViewPrice;
        TextView textViewOriginalPrice; // Thêm TextView cho giá gốc

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewGame = itemView.findViewById(R.id.imageViewGame);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewOriginalPrice = itemView.findViewById(R.id.textViewOriginalPrice); // Ánh xạ ID mới
        }
    }
}