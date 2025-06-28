// File: app/src/main/java/com/example/caoquangnhat_2123110077/LoginActivity.java
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

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText editTextUsername;
    private TextInputEditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewRegister = findViewById(R.id.textViewRegister);

        buttonLogin.setOnClickListener(v -> {
            String enteredUsername = editTextUsername.getText().toString().trim();
            String enteredPassword = editTextPassword.getText().toString().trim();

            if (TextUtils.isEmpty(enteredUsername) || TextUtils.isEmpty(enteredPassword)) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            // --- PHẦN SỬA ĐỔI: ĐỌC TÀI KHOẢN TỪ SHAREDPREFERENCES ĐỂ KIỂM TRA ---
            SharedPreferences sharedPreferences = getSharedPreferences(RegisterActivity.SHARED_PREFS, MODE_PRIVATE);
            String savedUsername = sharedPreferences.getString(RegisterActivity.KEY_USERNAME, null);
            String savedPassword = sharedPreferences.getString(RegisterActivity.KEY_PASSWORD, null);

            // Kiểm tra thông tin nhập vào với thông tin đã lưu
            if (enteredUsername.equals(savedUsername) && enteredPassword.equals(savedPassword)) {
                Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu không đúng.", Toast.LENGTH_SHORT).show();
            }
            // --- KẾT THÚC PHẦN SỬA ĐỔI ---
        });

        textViewRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}