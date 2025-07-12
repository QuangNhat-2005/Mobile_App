// File: app/src/main/java/com/example/caoquangnhat_2123110077/LoginActivity.java
// ĐÃ ĐƯỢC CHỈNH SỬA
package com.example.caoquangnhat_2123110077;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private TextView textViewRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.etLoginEmail);
        editTextPassword = findViewById(R.id.etLoginPassword);
        buttonLogin = findViewById(R.id.btnLogin);
        textViewRegister = findViewById(R.id.tvRegister);

        textViewRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        buttonLogin.setOnClickListener(v -> {
            loginUser();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Tự động đăng nhập nếu người dùng đã có phiên làm việc
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Người dùng đã đăng nhập, chuyển thẳng vào HomeActivity
            // và xóa lịch sử cũ
            goToHomeActivity();
        }
    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Đăng nhập thành công
                        Log.d("FIREBASE_AUTH", "signInWithEmail:success");
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công.", Toast.LENGTH_SHORT).show();

                        // Chuyển đến màn hình chính và xóa lịch sử cũ
                        goToHomeActivity();

                    } else {
                        // Nếu đăng nhập thất bại
                        Log.w("FIREBASE_AUTH", "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin.",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    // === BẮT ĐẦU THAY ĐỔI QUAN TRỌNG ===
    // Tạo một phương thức riêng để chuyển màn hình, có sử dụng cờ (flags)
    private void goToHomeActivity() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        // Cặp cờ này sẽ tạo một "task" mới cho HomeActivity và xóa tất cả các task cũ
        // Kết quả là HomeActivity trở thành màn hình gốc duy nhất.
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish(); // Đóng LoginActivity lại
    }
    // === KẾT THÚC THAY ĐỔI QUAN TRỌNG ===
}