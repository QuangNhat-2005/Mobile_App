package com.example.caoquangnhat_2123110077;

import android.content.Context;
import android.content.Intent;
import android.util.Log; // <-- THÊM IMPORT NÀY
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    private List<Game> gameList;
    private Context context;

    public GameAdapter(Context context, List<Game> gameList) {
        this.context = context;
        this.gameList = gameList;
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
        holder.textViewGameName.setText(game.getName());
        holder.textViewGamePrice.setText(game.getPrice());

        if (game.getImageResourceIds() != null && !game.getImageResourceIds().isEmpty()) {
            holder.imageViewGame.setImageResource(game.getImageResourceIds().get(0));
        }

        holder.itemView.setOnClickListener(v -> {
            // =================== THÊM DÒNG LỆNH THEO DÕI ===================
            Log.d("DEBUG_SEND", "Gửi game: " + game.getName() + " | Số lượng ảnh: " + game.getImageResourceIds().size());
            // =============================================================

            Intent intent = new Intent(context, GameDetailActivity.class);
            intent.putExtra("GAME_NAME", game.getName());
            intent.putExtra("GAME_PRICE", game.getPrice());
            intent.putExtra("GAME_CATEGORY", game.getCategory());
            intent.putExtra("GAME_DESCRIPTION", game.getDescription());
            intent.putExtra("GAME_RATING", game.getRating());
            intent.putIntegerArrayListExtra("GAME_IMAGE_IDS", new ArrayList<>(game.getImageResourceIds()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewGame;
        TextView textViewGameName;
        TextView textViewGamePrice;
        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewGame = itemView.findViewById(R.id.imageViewGame);
            textViewGameName = itemView.findViewById(R.id.textViewGameName);
            textViewGamePrice = itemView.findViewById(R.id.textViewGamePrice);
        }
    }

    public void filterList(List<Game> filteredList) {
        this.gameList = filteredList;
        notifyDataSetChanged();
    }
}