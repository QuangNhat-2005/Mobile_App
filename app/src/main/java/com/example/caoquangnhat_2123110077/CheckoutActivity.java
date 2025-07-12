// File: app/src/main/java/com/example/caoquangnhat_2123110077/CheckoutActivity.java
// ĐÃ ĐƯỢC CHỈNH SỬA
package com.example.caoquangnhat_2123110077;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCheckoutItems;
    private CheckoutAdapter checkoutAdapter;
    private TextView textUserEmail, textSubtotal, textDiscount, textTotalPriceBottom, textCheckoutWalletBalance;
    private Button buttonConfirmPayment;
    private RadioGroup paymentMethodGroup;
    private List<Game> selectedItems;
    private FirebaseAuth mAuth;

    private float currentUserBalance = 0f;
    private double finalTotal = 0.0;
    private boolean isBuyNowSession = false; // Thêm cờ để nhận biết phiên "Mua Ngay"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        mAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar_checkout);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Logic lấy danh sách game đã được cập nhật
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("BUY_NOW_GAME")) {
            isBuyNowSession = true;
            Game buyNowGame = (Game) intent.getSerializableExtra("BUY_NOW_GAME");
            selectedItems = new ArrayList<>();
            if (buyNowGame != null) {
                selectedItems.add(buyNowGame);
            }
        } else {
            isBuyNowSession = false;
            selectedItems = Cart.getInstance().getItems();
        }

        // Ánh xạ các view
        textUserEmail = findViewById(R.id.checkout_user_email);
        recyclerViewCheckoutItems = findViewById(R.id.checkout_recycler_view);
        textSubtotal = findViewById(R.id.checkout_subtotal);
        textDiscount = findViewById(R.id.checkout_discount);
        textTotalPriceBottom = findViewById(R.id.checkout_total_price_bottom);
        buttonConfirmPayment = findViewById(R.id.button_confirm_payment);
        paymentMethodGroup = findViewById(R.id.payment_method_group);
        textCheckoutWalletBalance = findViewById(R.id.text_checkout_wallet_balance);

        setupUserInfo();
        setupRecyclerView();
        calculateAndDisplayFinalPrice();

        buttonConfirmPayment.setOnClickListener(v -> processPayment());
    }

    private void processPayment() {
        String paymentMethod = getSelectedPaymentMethod();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "Lỗi: Không tìm thấy người dùng.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (paymentMethod.equals("Số dư Wallet")) {
            if (currentUserBalance < finalTotal) {
                Toast.makeText(this, "Số dư không đủ. Vui lòng chọn phương thức khác.", Toast.LENGTH_LONG).show();
                return;
            }
            float newBalance = currentUserBalance - (float)finalTotal;
            WalletManager.updateBalance(this, user.getUid(), newBalance);
            completeTransaction(paymentMethod);
        } else {
            completeTransaction(paymentMethod);
        }
    }

    private void completeTransaction(String paymentMethod) {
        String totalAmount = textTotalPriceBottom.getText().toString();
        long transactionId = System.currentTimeMillis();
        Transaction transaction = new Transaction(transactionId, System.currentTimeMillis(), new ArrayList<>(selectedItems), totalAmount, paymentMethod);
        TransactionManager.saveTransaction(this, transaction);
        for (Game purchasedItem : selectedItems) {
            LibraryManager.addGameToLibrary(this, purchasedItem);
        }

        // Chỉ xóa giỏ hàng nếu không phải là phiên "Mua Ngay"
        if (!isBuyNowSession) {
            Cart.getInstance().clearCart();
        }

        Toast.makeText(this, "Thanh toán thành công! Game đã được thêm vào thư viện.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    // Các phương thức còn lại giữ nguyên
    private String getSelectedPaymentMethod() {
        int selectedId = paymentMethodGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.radio_wallet) return "Số dư Wallet";
        if (selectedId == R.id.radio_card) return "Thẻ Tín dụng/Ghi nợ";
        if (selectedId == R.id.radio_paypal) return "PayPal";
        return "Không rõ";
    }

    private void setupUserInfo() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            textUserEmail.setText(currentUser.getEmail());
            currentUserBalance = WalletManager.getWalletBalance(this, currentUser.getUid());
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            textCheckoutWalletBalance.setText("(" + currencyFormat.format(currentUserBalance) + ")");
        } else {
            textUserEmail.setText("Không tìm thấy thông tin người dùng");
        }
    }

    private void setupRecyclerView() {
        recyclerViewCheckoutItems.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCheckoutItems.setNestedScrollingEnabled(false);
        checkoutAdapter = new CheckoutAdapter(selectedItems);
        recyclerViewCheckoutItems.setAdapter(checkoutAdapter);
    }

    private void calculateAndDisplayFinalPrice() {
        double subtotal = 0;
        double totalDiscount = 0;
        finalTotal = 0;
        for (Game game : selectedItems) {
            if (game.getPrice() == null || game.getPrice().equalsIgnoreCase("Free to Play")) continue;
            try {
                if (game.isOnSale()) {
                    double originalPrice = Double.parseDouble(game.getOriginalPrice().replaceAll("[^\\d]", ""));
                    double salePrice = Double.parseDouble(game.getPrice().replaceAll("[^\\d]", ""));
                    subtotal += originalPrice;
                    totalDiscount += (originalPrice - salePrice);
                    finalTotal += salePrice;
                } else {
                    double price = Double.parseDouble(game.getPrice().replaceAll("[^\\d]", ""));
                    subtotal += price;
                    finalTotal += price;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        textSubtotal.setText(currencyFormat.format(subtotal));
        textDiscount.setText(currencyFormat.format(totalDiscount));
        textTotalPriceBottom.setText(currencyFormat.format(finalTotal));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}