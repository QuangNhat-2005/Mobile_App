// File: app/src/main/java/com/example/caoquangnhat_2123110077/GameDetailActivity.java
// ĐÃ ĐƯỢC CHỈNH SỬA
package com.example.caoquangnhat_2123110077;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.bumptech.glide.Glide;

public class GameDetailActivity extends AppCompatActivity {

    private Game currentGame;
    private Button buttonBuyNow, buttonAddToCart;
    private TextView textGameName, textGamePrice, textGameOriginalPrice, textGameDescription, textSystemRequirements,
            textDeveloper, textPublisher, textReleaseDate, textGenre;
    private ImageView imageGameHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Ánh xạ các View
        textGameName = findViewById(R.id.text_game_name);
        textGamePrice = findViewById(R.id.text_game_price);
        textGameOriginalPrice = findViewById(R.id.text_game_original_price);
        textGameDescription = findViewById(R.id.text_game_description);
        textSystemRequirements = findViewById(R.id.text_system_requirements_content);
        imageGameHeader = findViewById(R.id.image_game_header);
        buttonBuyNow = findViewById(R.id.button_buy_now);
        buttonAddToCart = findViewById(R.id.button_add_to_cart);
        textDeveloper = findViewById(R.id.text_game_developer);
        textPublisher = findViewById(R.id.text_game_publisher);
        textReleaseDate = findViewById(R.id.text_game_release_date);
        textGenre = findViewById(R.id.text_game_genre);

        // Lấy dữ liệu game từ Intent
        currentGame = (Game) getIntent().getSerializableExtra("GAME_OBJECT");
        boolean isOwned = getIntent().getBooleanExtra("IS_OWNED", false);

        if (currentGame != null) {
            populateGameDetails();
            setupActionButtons(isOwned);
        }
    }

    private void populateGameDetails() {
        textGameName.setText(currentGame.getName());
        textGameDescription.setText(currentGame.getDescription());
        textSystemRequirements.setText(currentGame.getSystemRequirements());
        textDeveloper.setText(currentGame.getDeveloper());
        textPublisher.setText(currentGame.getPublisher());
        textReleaseDate.setText(currentGame.getReleaseDate());
        textGenre.setText(currentGame.getCategory());

        // Logic hiển thị giá mới của bạn (giữ nguyên)
        if (currentGame.isOnSale()) {
            textGamePrice.setText(currentGame.getPrice());
            textGamePrice.setTextColor(getResources().getColor(R.color.sale_price_color));
            textGameOriginalPrice.setVisibility(View.VISIBLE);
            textGameOriginalPrice.setText(currentGame.getOriginalPrice());
            textGameOriginalPrice.setPaintFlags(textGameOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            textGamePrice.setText(currentGame.getPrice());
            textGameOriginalPrice.setVisibility(View.GONE);
        }

        Glide.with(this)
                .load(currentGame.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageGameHeader);
    }

    private void setupActionButtons(boolean isOwned) {
        if (isOwned) {
            buttonAddToCart.setVisibility(View.GONE);
            if (DownloadManager.isGameDownloaded(this, currentGame.getName())) {
                buttonBuyNow.setText("Chơi");
                buttonBuyNow.setEnabled(true);
                buttonBuyNow.setOnClickListener(v -> Toast.makeText(this, "Đang khởi động " + currentGame.getName(), Toast.LENGTH_SHORT).show());
            } else {
                buttonBuyNow.setText("Tải xuống");
                buttonBuyNow.setEnabled(true);
                buttonBuyNow.setOnClickListener(v -> {
                    Toast.makeText(this, "Bắt đầu tải " + currentGame.getName(), Toast.LENGTH_SHORT).show();
                    DownloadManager.setGameDownloaded(this, currentGame.getName(), true);
                    buttonBuyNow.setText("Chơi");
                    buttonBuyNow.setOnClickListener(v2 -> Toast.makeText(this, "Đang khởi động " + currentGame.getName(), Toast.LENGTH_SHORT).show());
                });
            }
        } else {
            if (Cart.getInstance().isItemInCart(currentGame)) {
                updateButtonsAfterAddToCart();
            } else {
                buttonAddToCart.setText("Thêm vào giỏ");
                buttonAddToCart.setEnabled(true);
            }

            buttonAddToCart.setOnClickListener(v -> {
                Cart.getInstance().addItem(currentGame);
                Toast.makeText(this, "Đã thêm '" + currentGame.getName() + "' vào giỏ hàng", Toast.LENGTH_SHORT).show();
                updateButtonsAfterAddToCart();
            });

            // === BẮT ĐẦU THAY ĐỔI LOGIC NÚT "MUA NGAY" ===
            buttonBuyNow.setOnClickListener(v -> {
                // Không thêm vào giỏ hàng chung
                // Tạo Intent và gửi trực tiếp game này đến CheckoutActivity
                Intent intent = new Intent(this, CheckoutActivity.class);
                // Dùng một key đặc biệt để CheckoutActivity nhận biết đây là giao dịch "Mua Ngay"
                intent.putExtra("BUY_NOW_GAME", currentGame);
                startActivity(intent);
            });
            // === KẾT THÚC THAY ĐỔI LOGIC NÚT "MUA NGAY" ===
        }
    }

    private void updateButtonsAfterAddToCart() {
        buttonAddToCart.setText("Đã thêm vào giỏ");
        buttonAddToCart.setEnabled(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}