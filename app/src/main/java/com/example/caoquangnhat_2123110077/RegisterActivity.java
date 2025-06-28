// File: app/src/main/java/com/example/caoquangnhat_2123110077/RegisterActivity.java
package com.example.caoquangnhat_2123110077;

import android.content.Intent;
import android.content.SharedPreferences; // Thêm import
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText editTextUsername;
    private TextInputEditText editTextPassword;
    private TextInputEditText editTextConfirmPassword;
    private Button buttonRegister;
    private TextView textViewLoginLink;

    // --- PHẦN THÊM MỚI: ĐỊNH NGHĨA TÊN CHO SHAREDPREFERENCES ---
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    // --- KẾT THÚC PHẦN THÊM MỚI ---

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        textViewLoginLink = findViewById(R.id.textViewLoginLink);

        buttonRegister.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String confirmPassword = editTextConfirmPassword.getText().toString().trim();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                editTextConfirmPassword.setError("Mật khẩu xác nhận không khớp");
                editTextConfirmPassword.requestFocus();
                return;
            }

            // --- PHẦN SỬA ĐỔI: LƯU TÀI KHOẢN VÀO SHAREDPREFERENCES ---
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(KEY_USERNAME, username);
            editor.putString(KEY_PASSWORD, password);
            editor.apply(); // Lưu lại

            Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
            finish();
            // --- KẾT THÚC PHẦN SỬA ĐỔI ---
        });

        textViewLoginLink.setOnClickListener(v -> {
            finish();
        });
    }
}