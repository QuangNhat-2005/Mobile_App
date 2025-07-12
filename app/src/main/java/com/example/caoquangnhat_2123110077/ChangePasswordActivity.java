package com.example.caoquangnhat_2123110077;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    // === BẮT ĐẦU THAY ĐỔI: Thêm biến cho mật khẩu hiện tại ===
    private TextInputEditText editTextCurrentPassword, editTextNewPassword, editTextConfirmPassword;
    // === KẾT THÚC THAY ĐỔI ===
    private Button buttonUpdatePassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar_change_password);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // === BẮT ĐẦU THAY ĐỔI: Ánh xạ view mới ===
        editTextCurrentPassword = findViewById(R.id.edit_text_current_password);
        // === KẾT THÚC THAY ĐỔI ===
        editTextNewPassword = findViewById(R.id.edit_text_new_password);
        editTextConfirmPassword = findViewById(R.id.edit_text_confirm_password);
        buttonUpdatePassword = findViewById(R.id.button_update_password);

        buttonUpdatePassword.setOnClickListener(v -> attemptChangePassword());
    }

    private void attemptChangePassword() {
        // === BẮT ĐẦU THAY ĐỔI: Lấy thêm mật khẩu hiện tại ===
        String currentPassword = editTextCurrentPassword.getText().toString().trim();
        // === KẾT THÚC THAY ĐỔI ===
        String newPassword = editTextNewPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        // --- Kiểm tra các trường nhập liệu ---
        if (TextUtils.isEmpty(currentPassword)) {
            editTextCurrentPassword.setError("Vui lòng nhập mật khẩu hiện tại");
            return;
        }
        if (TextUtils.isEmpty(newPassword) || newPassword.length() < 6) {
            editTextNewPassword.setError("Mật khẩu mới phải có ít nhất 6 ký tự");
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            editTextConfirmPassword.setError("Mật khẩu xác nhận không khớp");
            return;
        }
        if (newPassword.equals(currentPassword)) {
            editTextNewPassword.setError("Mật khẩu mới phải khác mật khẩu hiện tại");
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null && user.getEmail() != null) {
            // --- BẮT ĐẦU QUÁ TRÌNH XÁC THỰC LẠI ---
            // Tạo một "chứng chỉ" từ email và mật khẩu hiện tại người dùng nhập vào
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);

            // Yêu cầu Firebase xác thực lại với chứng chỉ này
            user.reauthenticate(credential).addOnCompleteListener(reauthTask -> {
                if (reauthTask.isSuccessful()) {
                    // --- XÁC THỰC LẠI THÀNH CÔNG ---
                    // Bây giờ mới tiến hành đổi mật khẩu mới
                    user.updatePassword(newPassword).addOnCompleteListener(updateTask -> {
                        if (updateTask.isSuccessful()) {
                            Toast.makeText(this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                            finish(); // Đóng màn hình
                        } else {
                            Toast.makeText(this, "Lỗi khi cập nhật mật khẩu: " + updateTask.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    // --- XÁC THỰC LẠI THẤT BẠI ---
                    // Lý do phổ biến nhất là nhập sai mật khẩu hiện tại
                    editTextCurrentPassword.setError("Mật khẩu hiện tại không đúng.");
                    Toast.makeText(this, "Xác thực thất bại. Vui lòng kiểm tra lại mật khẩu hiện tại.", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}