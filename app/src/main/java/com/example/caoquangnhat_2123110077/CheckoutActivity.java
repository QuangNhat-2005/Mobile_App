package com.example.caoquangnhat_2123110077;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    private ArrayList<Game> selectedItems;
    private RecyclerView recyclerViewCheckoutItems;
    private CheckoutAdapter checkoutAdapter;
    private TextView textViewFinalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Toolbar toolbar = findViewById(R.id.toolbarCheckout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectedItems = (ArrayList<Game>) getIntent().getSerializableExtra("SELECTED_ITEMS");
        if (selectedItems == null) {
            selectedItems = new ArrayList<>();
        }

        recyclerViewCheckoutItems = findViewById(R.id.recyclerViewCheckoutItems);
        textViewFinalPrice = findViewById(R.id.textViewFinalPrice);
        Button buttonConfirmPurchase = findViewById(R.id.buttonConfirmPurchase);

        setupRecyclerView();
        calculateAndDisplayFinalPrice();

        buttonConfirmPurchase.setOnClickListener(v -> {
            // --- CẬP NHẬT LOGIC Ở ĐÂY ---
            for (Game purchasedItem : selectedItems) {
                // 1. Xóa các sản phẩm đã mua khỏi giỏ hàng chính
                Cart.removeItem(purchasedItem);
                // 2. LƯU GAME VÀO THƯ VIỆN
                LibraryManager.addGame(this, purchasedItem);
            }

            Toast.makeText(this, "Thanh toán thành công!", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void setupRecyclerView() {
        recyclerViewCheckoutItems.setLayoutManager(new LinearLayoutManager(this));
        checkoutAdapter = new CheckoutAdapter(selectedItems);
        recyclerViewCheckoutItems.setAdapter(checkoutAdapter);
    }

    private void calculateAndDisplayFinalPrice() {
        long totalPrice = 0;
        for (Game game : selectedItems) {
            try {
                String priceString = game.getPrice().replaceAll("[^\\d]", "");
                totalPrice += Long.parseLong(priceString);
            } catch (NumberFormatException e) {
                // Bỏ qua nếu giá không hợp lệ
            }
        }
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        textViewFinalPrice.setText("Tổng cộng: " + format.format(totalPrice));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}