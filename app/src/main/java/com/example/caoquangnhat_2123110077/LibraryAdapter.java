// File: app/src/main/java/com/example/caoquangnhat_2123110077/LibraryAdapter.java
// ĐÃ ĐƯỢC CHỈNH SỬA
package com.example.caoquangnhat_2123110077;

import android.content.Context;
import android.content.Intent; // Thêm import này
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder> {

    private final Context context;
    private final List<Game> libraryGames;

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

        Glide.with(context)
                .load(game.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.gameImage);

        // Kiểm tra trạng thái tải về và cập nhật nút
        if (DownloadManager.isGameDownloaded(context, game.getName())) {
            holder.actionButton.setText("Chơi");
        } else {
            holder.actionButton.setText("Tải xuống");
        }

        holder.actionButton.setOnClickListener(v -> {
            if (DownloadManager.isGameDownloaded(context, game.getName())) {
                Toast.makeText(context, "Đang mở " + game.getName(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Bắt đầu tải " + game.getName(), Toast.LENGTH_SHORT).show();
                DownloadManager.setGameDownloaded(context, game.getName(), true);
                holder.actionButton.setText("Chơi"); // Cập nhật nút ngay lập tức
            }
        });

        // === BẮT ĐẦU THAY ĐỔI ===
        // Thêm sự kiện click cho toàn bộ item view
        holder.itemView.setOnClickListener(v -> {
            // Tạo Intent để mở GameDetailActivity
            Intent intent = new Intent(context, GameDetailActivity.class);

            // Đính kèm đối tượng game vào Intent.
            // Sử dụng key "GAME_OBJECT" vì GameDetailActivity của bạn đang dùng key này.
            intent.putExtra("GAME_OBJECT", game);

            // Vì đây là thư viện, game chắc chắn đã được sở hữu.
            // Gửi cờ IS_OWNED = true để GameDetailActivity biết và hiển thị đúng các nút.
            intent.putExtra("IS_OWNED", true);

            // Khởi chạy Activity
            context.startActivity(intent);
        });
        // === KẾT THÚC THAY ĐỔI ===
    }

    @Override
    public int getItemCount() {
        return libraryGames.size();
    }

    public static class LibraryViewHolder extends RecyclerView.ViewHolder {
        ImageView gameImage;
        TextView gameName, gameStatus;
        Button actionButton;

        public LibraryViewHolder(@NonNull View itemView) {
            super(itemView);
            gameImage = itemView.findViewById(R.id.library_game_image);
            gameName = itemView.findViewById(R.id.library_game_name);
            gameStatus = itemView.findViewById(R.id.library_game_status);
            actionButton = itemView.findViewById(R.id.library_action_button);
        }
    }
}