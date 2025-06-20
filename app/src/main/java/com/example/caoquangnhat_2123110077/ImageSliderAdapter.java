package com.example.caoquangnhat_2123110077;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.SliderViewHolder> {

    private List<Integer> imageIds;
    private OnImageClickListener listener; // <-- Biến để lưu listener

    // Interface để giao tiếp với Activity
    public interface OnImageClickListener {
        void onImageClick(int imageResourceId);
    }

    // Cập nhật Constructor để nhận listener
    public ImageSliderAdapter(List<Integer> imageIds, OnImageClickListener listener) {
        this.imageIds = imageIds;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // SỬ DỤNG LAYOUT MỚI CHO THUMBNAIL
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gameplay_thumbnail, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        int imageId = imageIds.get(position);
        holder.imageView.setImageResource(imageId);

        // Thiết lập sự kiện click
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onImageClick(imageId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageIds.size();
    }

    public static class SliderViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ ImageView từ layout thumbnail mới
            imageView = itemView.findViewById(R.id.imageViewThumbnail);
        }
    }
}