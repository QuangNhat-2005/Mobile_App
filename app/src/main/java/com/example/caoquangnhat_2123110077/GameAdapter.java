// File: app/src/main/java/com/example/caoquangnhat_2123110077/GameAdapter.java
package com.example.caoquangnhat_2123110077;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Set; // Thêm import

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    private List<Game> gameList;
    private Context context;
    private Set<String> ownedGameNames; // --- THÊM MỚI: Biến lưu danh sách game đã mua ---

    // --- SỬA ĐỔI: Constructor nhận thêm danh sách game đã mua ---
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
        holder.textViewPrice.setText(game.getPrice());
        holder.ratingBar.setRating(game.getRating());

        if (game.getImageResourceIds() != null && !game.getImageResourceIds().isEmpty()) {
            holder.imageView.setImageResource(game.getImageResourceIds().get(0));
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, GameDetailActivity.class);
            intent.putExtra("GAME_OBJECT", game);

            // --- SỬA ĐỔI QUAN TRỌNG: KIỂM TRA XEM GAME ĐÃ ĐƯỢC SỞ HỮU CHƯA ---
            boolean isOwned = ownedGameNames.contains(game.getName());
            intent.putExtra("IS_OWNED", isOwned);
            // --- KẾT THÚC SỬA ĐỔI ---

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    // --- THÊM MỚI: Hàm để cập nhật danh sách game đã mua từ bên ngoài ---
    public void updateOwnedGames(Set<String> newOwnedGameNames) {
        this.ownedGameNames = newOwnedGameNames;
        notifyDataSetChanged(); // Cập nhật lại toàn bộ list để đảm bảo giao diện đúng
    }

    public void filterList(List<Game> filteredList) {
        this.gameList = filteredList;
        notifyDataSetChanged();
    }

    // --- SỬA ĐỔI: Cập nhật ViewHolder cho đúng với layout item_game.xml của bạn ---
    public static class GameViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewName;
        TextView textViewPrice;
        RatingBar ratingBar;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewGame);
            textViewName = itemView.findViewById(R.id.textViewGameName);
            textViewPrice = itemView.findViewById(R.id.textViewGamePrice);
            ratingBar = itemView.findViewById(R.id.ratingBar); // Giả sử ID của RatingBar là ratingBar
        }
    }
}