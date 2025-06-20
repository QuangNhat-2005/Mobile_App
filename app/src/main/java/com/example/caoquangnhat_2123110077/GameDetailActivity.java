package com.example.caoquangnhat_2123110077;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class GameDetailActivity extends AppCompatActivity implements ImageSliderAdapter.OnImageClickListener {

    private ImageView imageViewCover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        String name = intent.getStringExtra("GAME_NAME");
        String price = intent.getStringExtra("GAME_PRICE");
        String category = intent.getStringExtra("GAME_CATEGORY");
        String description = intent.getStringExtra("GAME_DESCRIPTION");
        float rating = intent.getFloatExtra("GAME_RATING", 0f);
        ArrayList<Integer> imageIds = intent.getIntegerArrayListExtra("GAME_IMAGE_IDS");

        if (name != null) {
            setTitle(name);

            // Ánh xạ các view
            imageViewCover = findViewById(R.id.imageViewCover);
            RecyclerView recyclerViewGameplay = findViewById(R.id.recyclerViewGameplay);
            TextView textViewGameplayTitle = findViewById(R.id.textViewGameplayTitle);
            TextView textViewNameDetail = findViewById(R.id.textViewNameDetail);
            TextView textViewCategoryDetail = findViewById(R.id.textViewCategoryDetail);
            RatingBar ratingBarDetail = findViewById(R.id.ratingBarDetail);
            TextView textViewDescriptionDetail = findViewById(R.id.textViewDescriptionDetail);
            TextView textViewPriceDetail = findViewById(R.id.textViewPriceDetail);
            Button buttonBuy = findViewById(R.id.buttonBuy);

            // Xử lý hiển thị ảnh
            if (imageIds != null && !imageIds.isEmpty()) {
                imageViewCover.setImageResource(imageIds.get(0));

                if (imageIds.size() > 1) {
                    textViewGameplayTitle.setVisibility(View.VISIBLE);
                    recyclerViewGameplay.setVisibility(View.VISIBLE);

                    List<Integer> gameplayImages = imageIds.subList(1, imageIds.size());
                    ImageSliderAdapter gameplayAdapter = new ImageSliderAdapter(gameplayImages, this);
                    recyclerViewGameplay.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                    recyclerViewGameplay.setAdapter(gameplayAdapter);
                } else {
                    textViewGameplayTitle.setVisibility(View.GONE);
                    recyclerViewGameplay.setVisibility(View.GONE);
                }
            }

            // =================== PHẦN BỊ THIẾU ĐÃ ĐƯỢC KHÔI PHỤC ===================
            // Điền dữ liệu vào các view
            textViewNameDetail.setText(name);
            textViewCategoryDetail.setText(category);
            ratingBarDetail.setRating(rating);
            textViewDescriptionDetail.setText(description);
            textViewPriceDetail.setText(price);
            // =====================================================================

            // Xử lý sự kiện cho nút Mua
            buttonBuy.setOnClickListener(v -> {
                Toast.makeText(this, "Đã thêm " + name + " vào giỏ hàng!", Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public void onImageClick(int imageResourceId) {
        // Khi một thumbnail được nhấn, cập nhật ảnh bìa lớn
        imageViewCover.setImageResource(imageResourceId);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}