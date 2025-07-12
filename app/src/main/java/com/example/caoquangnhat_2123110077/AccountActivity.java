// File: app/src/main/java/com/example/caoquangnhat_2123110077/AccountActivity.java
// ĐÃ ĐƯỢC CHỈNH SỬA
package com.example.caoquangnhat_2123110077;

import android.content.Intent;
import android.content.SharedPreferences; // Thêm import này
import android.net.Uri; // Thêm import này
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher; // Thêm import này
import androidx.activity.result.PickVisualMediaRequest; // Thêm import này
import androidx.activity.result.contract.ActivityResultContracts; // Thêm import này
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.bumptech.glide.Glide; // Thêm import này
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.text.NumberFormat;
import java.util.Locale;
import de.hdodenhof.circleimageview.CircleImageView; // Thêm import này

public class AccountActivity extends AppCompatActivity {

    private TextView textUserEmail, textAccountBalance;
    private RelativeLayout layoutLibrary, layoutTransactionHistory, layoutChangePassword;
    private Button buttonLogout, buttonAddFunds;
    private CircleImageView imageAccountAvatar; // Đổi thành CircleImageView
    private FirebaseAuth mAuth;

    // === BẮT ĐẦU THÊM: Trình chọn ảnh hiện đại ===
    private ActivityResultLauncher<PickVisualMediaRequest> pickMediaLauncher;
    private static final String PREFS_NAME = "UserPrefs";
    private static final String AVATAR_URI_KEY = "avatarUri";
    // === KẾT THÚC THÊM ===

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar_account);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Ánh xạ các view
        textUserEmail = findViewById(R.id.text_account_email);
        textAccountBalance = findViewById(R.id.text_account_balance);
        layoutLibrary = findViewById(R.id.layout_library);
        layoutTransactionHistory = findViewById(R.id.layout_transaction_history);
        layoutChangePassword = findViewById(R.id.layout_change_password);
        buttonLogout = findViewById(R.id.button_account_logout);
        buttonAddFunds = findViewById(R.id.button_add_funds);
        imageAccountAvatar = findViewById(R.id.image_account_avatar); // Ánh xạ avatar

        // === BẮT ĐẦU THÊM: Khởi tạo trình chọn ảnh ===
        setupImagePicker();
        // === KẾT THÚC THÊM ===

        setupUserInfo();

        // Sự kiện click
        layoutLibrary.setOnClickListener(v -> startActivity(new Intent(this, LibraryActivity.class)));
        buttonLogout.setOnClickListener(v -> showLogoutConfirmationDialog());
        layoutChangePassword.setOnClickListener(v -> startActivity(new Intent(this, ChangePasswordActivity.class)));
        layoutTransactionHistory.setOnClickListener(v -> startActivity(new Intent(this, TransactionHistoryActivity.class)));
        buttonAddFunds.setOnClickListener(v -> showAddFundsDialog());

        // === BẮT ĐẦU THÊM: Sự kiện click cho avatar ===
        imageAccountAvatar.setOnClickListener(v -> {
            pickMediaLauncher.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });
        // === KẾT THÚC THÊM ===
    }

    private void setupUserInfo() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            textUserEmail.setText(currentUser.getEmail());
            updateBalanceDisplay();
            loadAvatar(); // Tải avatar đã lưu
        } else {
            performLogout();
        }
    }

    // === BẮT ĐẦU THÊM CÁC PHƯƠNG THỨC MỚI ===

    /**
     * Khởi tạo ActivityResultLauncher để xử lý kết quả chọn ảnh.
     */
    private void setupImagePicker() {
        pickMediaLauncher = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                // Người dùng đã chọn một ảnh
                Glide.with(this).load(uri).into(imageAccountAvatar);
                saveAvatarUri(uri.toString()); // Lưu URI của ảnh
            } else {
                // Người dùng không chọn ảnh nào
                Toast.makeText(this, "Không có ảnh nào được chọn", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Lưu URI của ảnh đại diện vào SharedPreferences.
     * @param uriString URI của ảnh dưới dạng chuỗi.
     */
    private void saveAvatarUri(String uriString) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            // Lưu URI với key là sự kết hợp của key chung và UID của người dùng
            editor.putString(AVATAR_URI_KEY + "_" + user.getUid(), uriString);
            editor.apply();
        }
    }

    /**
     * Tải và hiển thị ảnh đại diện đã được lưu.
     */
    private void loadAvatar() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            String uriString = prefs.getString(AVATAR_URI_KEY + "_" + user.getUid(), null);
            if (uriString != null) {
                Uri avatarUri = Uri.parse(uriString);
                Glide.with(this).load(avatarUri).into(imageAccountAvatar);
            }
            // Nếu không có URI nào được lưu, nó sẽ giữ nguyên ảnh mặc định từ XML.
        }
    }

    // Các phương thức cũ giữ nguyên và được tối ưu một chút
    private void showAddFundsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nạp tiền vào Wallet");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setHint("Nhập số tiền muốn nạp");
        builder.setView(input);
        builder.setPositiveButton("Xác nhận", (dialog, which) -> {
            String amountString = input.getText().toString();
            if (!amountString.isEmpty()) {
                try {
                    float amountToAdd = Float.parseFloat(amountString);
                    if (amountToAdd > 0) {
                        addFundsToWallet(amountToAdd);
                    } else {
                        Toast.makeText(this, "Số tiền phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Số tiền không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void addFundsToWallet(float amountToAdd) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            float currentBalance = WalletManager.getWalletBalance(this, currentUser.getUid());
            float newBalance = currentBalance + amountToAdd;
            WalletManager.updateBalance(this, currentUser.getUid(), newBalance);
            updateBalanceDisplay();
            Toast.makeText(this, "Nạp tiền thành công!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateBalanceDisplay() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            float balance = WalletManager.getWalletBalance(this, currentUser.getUid());
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            textAccountBalance.setText(currencyFormat.format(balance));
        }
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Đăng xuất")
                .setMessage("Bạn có chắc chắn muốn đăng xuất không?")
                .setPositiveButton("Đăng xuất", (dialog, which) -> performLogout())
                .setNegativeButton("Hủy", null)
                .setIcon(R.drawable.ic_logout)
                .show();
    }

    private void performLogout() {
        mAuth.signOut();
        Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}