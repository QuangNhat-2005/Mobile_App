// File: app/src/main/java/com/example/caoquangnhat_2123110077/CartActivity.java
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
import java.util.List;
import java.util.Locale;

// === THAY ĐỔI: Implement interface độc lập, không còn lỗi cyclic ===
public class CartActivity extends AppCompatActivity implements CartInteractionListener {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private List<Game> cartItems;
    private TextView textViewTotalPrice;
    private Button buttonCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Giỏ Hàng");

        textViewTotalPrice = findViewById(R.id.textViewTotalPrice);
        buttonCheckout = findViewById(R.id.buttonCheckout);
        recyclerView = findViewById(R.id.recyclerViewCart);

        cartItems = Cart.getInstance().getItems();

        // Truyền "this" làm listener, bây giờ nó đã hợp lệ
        adapter = new CartAdapter(this, cartItems, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        updateTotalPrice();

        buttonCheckout.setOnClickListener(v -> {
            if (Cart.getInstance().getSelectedItems().isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ít nhất một sản phẩm để thanh toán", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(this, CheckoutActivity.class));
            }
        });
    }

    private void updateTotalPrice() {
        double total = 0;
        for (Game game : Cart.getInstance().getSelectedItems()) {
            try {
                String priceString = game.getPrice().replaceAll("[^\\d]", "");
                if (!priceString.isEmpty()) {
                    total += Double.parseDouble(priceString);
                }
            } catch (NumberFormatException e) {
                // Bỏ qua nếu giá không phải là số
            }
        }
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        textViewTotalPrice.setText("Tổng cộng: " + currencyFormat.format(total));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // Triển khai các phương thức của interface
    @Override
    public void onItemDeleted(Game game, int position) {
        Cart.getInstance().removeItem(game);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, cartItems.size());
        updateTotalPrice();
        Toast.makeText(this, "Đã xóa " + game.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelectionChanged(Game game, boolean isSelected) {
        game.setSelected(isSelected);
        updateTotalPrice();
    }
}