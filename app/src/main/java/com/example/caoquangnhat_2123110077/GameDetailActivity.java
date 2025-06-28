// File: app/src/main/java/com/example/caoquangnhat_2123110077/GameDetailActivity.java
package com.example.caoquangnhat_2123110077;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class GameDetailActivity extends AppCompatActivity implements ImageSliderAdapter.OnImageClickListener {

    private ImageView imageViewCover;
    private Game currentGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        boolean isOwned = intent.getBooleanExtra("IS_OWNED", false);

        if (intent.hasExtra("GAME_OBJECT")) {
            currentGame = (Game) intent.getSerializableExtra("GAME_OBJECT");
        } else {
            // Fallback nếu không có object được truyền qua
            ArrayList<Integer> imageIds = intent.getIntegerArrayListExtra("GAME_IMAGE_IDS");
            if (imageIds == null) {
                imageIds = new ArrayList<>();
            }
            // --- CẬP NHẬT LẠI HÀM KHỞI TẠO VỚI THAM SỐ THỨ 7 LÀ NULL ---
            currentGame = new Game(
                    intent.getStringExtra("GAME_NAME"),
                    intent.getStringExtra("GAME_PRICE"),
                    imageIds,
                    intent.getStringExtra("GAME_CATEGORY"),
                    intent.getStringExtra("GAME_DESCRIPTION"),
                    intent.getFloatExtra("GAME_RATING", 0f),
                    null // Giá trị mặc định cho systemRequirements
            );
        }

        if (currentGame != null && currentGame.getName() != null) {
            setTitle(currentGame.getName());

            LinearLayout bottomBar = findViewById(R.id.bottom_bar);
            MaterialButton buttonDownloadOwned = findViewById(R.id.buttonDownloadOwned);

            if (isOwned) {
                bottomBar.setVisibility(View.GONE);
                buttonDownloadOwned.setVisibility(View.VISIBLE);

                if (DownloadManager.isGameDownloaded(this, currentGame)) {
                    buttonDownloadOwned.setText("Chơi");
                    buttonDownloadOwned.setIconResource(R.drawable.ic_play_arrow);
                } else {
                    buttonDownloadOwned.setText("Tải xuống");
                    buttonDownloadOwned.setIconResource(R.drawable.ic_download);
                }

            } else {
                bottomBar.setVisibility(View.VISIBLE);
                buttonDownloadOwned.setVisibility(View.GONE);
            }

            buttonDownloadOwned.setOnClickListener(v -> {
                if (DownloadManager.isGameDownloaded(this, currentGame)) {
                    Toast.makeText(this, "Đang mở " + currentGame.getName() + "...", Toast.LENGTH_SHORT).show();
                } else {
                    DownloadManager.addDownloadedGame(this, currentGame);
                    Toast.makeText(this, "Đã tải xong " + currentGame.getName(), Toast.LENGTH_SHORT).show();
                    buttonDownloadOwned.setText("Chơi");
                    buttonDownloadOwned.setIconResource(R.drawable.ic_play_arrow);
                }
            });

            imageViewCover = findViewById(R.id.imageViewCover);
            RecyclerView recyclerViewGameplay = findViewById(R.id.recyclerViewGameplay);
            TextView textViewGameplayTitle = findViewById(R.id.textViewGameplayTitle);
            TextView textViewNameDetail = findViewById(R.id.textViewNameDetail);
            TextView textViewCategoryDetail = findViewById(R.id.textViewCategoryDetail);
            RatingBar ratingBarDetail = findViewById(R.id.ratingBarDetail);
            TextView textViewDescriptionDetail = findViewById(R.id.textViewDescriptionDetail);
            TextView textViewPriceDetail = findViewById(R.id.textViewPriceDetail);
            Button buttonAddToCart = findViewById(R.id.buttonAddToCart);
            Button buttonBuyNow = findViewById(R.id.buttonBuyNow);

            // --- PHẦN THÊM MỚI: ÁNH XẠ VIEW CẤU HÌNH ---
            TextView textViewSysReqTitle = findViewById(R.id.textViewSysReqTitle);
            TextView textViewSysReqDetail = findViewById(R.id.textViewSysReqDetail);


            textViewNameDetail.setText(currentGame.getName());
            textViewCategoryDetail.setText(currentGame.getCategory());
            ratingBarDetail.setRating(currentGame.getRating());
            textViewDescriptionDetail.setText(currentGame.getDescription());
            textViewPriceDetail.setText(currentGame.getPrice());

            if (!currentGame.getImageResourceIds().isEmpty()) {
                imageViewCover.setImageResource(currentGame.getImageResourceIds().get(0));
                if (currentGame.getImageResourceIds().size() > 1) {
                    textViewGameplayTitle.setVisibility(View.VISIBLE);
                    recyclerViewGameplay.setVisibility(View.VISIBLE);
                    List<Integer> gameplayImages = currentGame.getImageResourceIds().subList(1, currentGame.getImageResourceIds().size());
                    ImageSliderAdapter gameplayAdapter = new ImageSliderAdapter(gameplayImages, this);
                    recyclerViewGameplay.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                    recyclerViewGameplay.setAdapter(gameplayAdapter);
                } else {
                    textViewGameplayTitle.setVisibility(View.GONE);
                    recyclerViewGameplay.setVisibility(View.GONE);
                }
            }

            // --- PHẦN THÊM MỚI: HIỂN THỊ DỮ LIỆU CẤU HÌNH ---
            String sysReqs = currentGame.getSystemRequirements();
            if (sysReqs != null && !sysReqs.isEmpty()) {
                textViewSysReqTitle.setVisibility(View.VISIBLE);
                textViewSysReqDetail.setVisibility(View.VISIBLE);
                textViewSysReqDetail.setText(sysReqs);
            } else {
                textViewSysReqTitle.setVisibility(View.GONE);
                textViewSysReqDetail.setVisibility(View.GONE);
            }


            buttonAddToCart.setOnClickListener(v -> {
                if (Cart.addItem(currentGame)) {
                    Toast.makeText(this, "Đã thêm '" + currentGame.getName() + "' vào giỏ hàng", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Sản phẩm đã có trong giỏ hàng", Toast.LENGTH_SHORT).show();
                }
            });

            buttonBuyNow.setOnClickListener(v -> {
                ArrayList<Game> itemToCheckout = new ArrayList<>();
                itemToCheckout.add(currentGame);
                Intent checkoutIntent = new Intent(this, CheckoutActivity.class);
                checkoutIntent.putExtra("SELECTED_ITEMS", itemToCheckout);
                startActivity(checkoutIntent);
            });
        }
    }

    @Override
    public void onImageClick(int imageResourceId) {
        imageViewCover.setImageResource(imageResourceId);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}