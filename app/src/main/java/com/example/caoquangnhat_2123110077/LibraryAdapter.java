// File: app/src/main/java/com/example/caoquangnhat_2123110077/LibraryAdapter.java
package com.example.caoquangnhat_2123110077;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton; // Sử dụng MaterialButton để có icon
import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder> {

    private final List<Game> libraryGames;
    private final Context context;

    public LibraryAdapter(Context context, List<Game> libraryGames) {
        this.context = context;
        this.libraryGames = libraryGames;
    }

    @NonNull
    @Override
    public LibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_library_game, parent, false);
        return new LibraryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryViewHolder holder, int position) {
        Game game = libraryGames.get(position);
        holder.gameName.setText(game.getName());
        if (game.getImageResourceIds() != null && !game.getImageResourceIds().isEmpty()) {
            holder.gameImage.setImageResource(game.getImageResourceIds().get(0));
        }

        // --- BẮT ĐẦU PHẦN SỬA ĐỔI QUAN TRỌNG ---

        // 1. Kiểm tra trạng thái tải xuống và cập nhật giao diện ban đầu
        if (DownloadManager.isGameDownloaded(context, game)) {
            holder.downloadButton.setText("Chơi");
            holder.downloadButton.setIconResource(R.drawable.ic_play_arrow); // Icon "Play"
        } else {
            holder.downloadButton.setText("Tải xuống");
            holder.downloadButton.setIconResource(R.drawable.ic_download); // Icon "Download"
        }

        // 2. Cập nhật logic khi nhấn nút
        holder.downloadButton.setOnClickListener(v -> {
            // Kiểm tra lại trạng thái ngay tại thời điểm nhấn nút
            if (DownloadManager.isGameDownloaded(context, game)) {
                // Nếu đã tải -> Hành động "Chơi"
                Toast.makeText(context, "Đang mở " + game.getName() + "...", Toast.LENGTH_SHORT).show();
            } else {
                // Nếu chưa tải -> Hành động "Tải xuống"
                DownloadManager.addDownloadedGame(context, game); // Lưu trạng thái đã tải
                Toast.makeText(context, "Đã tải xong " + game.getName(), Toast.LENGTH_SHORT).show();

                // Cập nhật lại giao diện của nút ngay lập tức
                holder.downloadButton.setText("Chơi");
                holder.downloadButton.setIconResource(R.drawable.ic_play_arrow);
            }
        });

        // --- KẾT THÚC PHẦN SỬA ĐỔI ---

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, GameDetailActivity.class);
            intent.putExtra("GAME_OBJECT", game);
            intent.putExtra("IS_OWNED", true);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return libraryGames.size();
    }

    // --- SỬA ĐỔI: Đảm bảo ViewHolder sử dụng MaterialButton ---
    static class LibraryViewHolder extends RecyclerView.ViewHolder {
        ImageView gameImage;
        TextView gameName;
        MaterialButton downloadButton; // Đổi kiểu từ Button sang MaterialButton

        public LibraryViewHolder(@NonNull View itemView) {
            super(itemView);
            gameImage = itemView.findViewById(R.id.imageViewLibraryGame);
            gameName = itemView.findViewById(R.id.textViewLibraryGameName);
            downloadButton = itemView.findViewById(R.id.buttonDownload); // ID của nút trong item_library_game.xml
        }
    }
}