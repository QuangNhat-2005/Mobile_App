package com.example.caoquangnhat_2123110077;

import android.content.Intent; // Thêm import này
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.NumberFormat;
import java.util.ArrayList; // Thêm import này
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnCartItemChangeListener {

    private RecyclerView recyclerViewCart;
    private CartAdapter cartAdapter;
    private List<Game> cartItems;
    private TextView textViewTotalPrice, textViewEmptyCart;
    private View bottomLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar = findViewById(R.id.toolbarCart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerViewCart = findViewById(R.id.recyclerViewCart);
        textViewTotalPrice = findViewById(R.id.textViewTotalPrice);
        textViewEmptyCart = findViewById(R.id.textViewEmptyCart);
        bottomLayout = findViewById(R.id.bottomLayout);
        Button buttonCheckout = findViewById(R.id.buttonCheckout);

        cartItems = Cart.getItems();
        setupRecyclerView();
        updateCartState();

        // --- CẬP NHẬT LOGIC CHO NÚT THANH TOÁN ---
        buttonCheckout.setOnClickListener(v -> {
            // 1. Tạo một danh sách mới chỉ chứa các sản phẩm được chọn
            ArrayList<Game> selectedItems = new ArrayList<>();
            for (Game game : cartItems) {
                if (game.isSelected()) {
                    selectedItems.add(game);
                }
            }

            // 2. Kiểm tra xem có sản phẩm nào được chọn không
            if (selectedItems.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ít nhất một sản phẩm để thanh toán.", Toast.LENGTH_SHORT).show();
            } else {
                // 3. Nếu có, tạo Intent và gửi danh sách này sang CheckoutActivity
                Intent intent = new Intent(this, CheckoutActivity.class);
                // Gửi cả danh sách các game đã chọn
                intent.putExtra("SELECTED_ITEMS", selectedItems);
                startActivity(intent);
            }
        });
    }

    private void setupRecyclerView() {
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(this, cartItems, this);
        recyclerViewCart.setAdapter(cartAdapter);
    }

    private void updateCartState() {
        if (cartItems.isEmpty()) {
            textViewEmptyCart.setVisibility(View.VISIBLE);
            recyclerViewCart.setVisibility(View.GONE);
            bottomLayout.setVisibility(View.GONE);
        } else {
            textViewEmptyCart.setVisibility(View.GONE);
            recyclerViewCart.setVisibility(View.VISIBLE);
            bottomLayout.setVisibility(View.VISIBLE);
            calculateTotalPrice();
        }
    }

    private void calculateTotalPrice() {
        long totalPrice = 0;
        for (Game game : cartItems) {
            if (game.isSelected()) {
                try {
                    String priceString = game.getPrice().replaceAll("[^\\d]", "");
                    totalPrice += Long.parseLong(priceString);
                } catch (NumberFormatException e) {
                    // Bỏ qua nếu giá không hợp lệ
                }
            }
        }
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        textViewTotalPrice.setText("Tổng cộng: " + format.format(totalPrice));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onItemRemoved(Game game) {
        Cart.removeItem(game);
        cartAdapter.notifyDataSetChanged();
        updateCartState();
        Toast.makeText(this, "Đã xóa " + game.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSelectionChanged() {
        calculateTotalPrice();
    }
}