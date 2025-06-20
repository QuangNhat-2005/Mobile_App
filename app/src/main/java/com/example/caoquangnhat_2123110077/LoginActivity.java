package com.example.caoquangnhat_2123110077; // Thay thế bằng tên package của bạn

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences; // Thêm import cho SharedPreferences
import android.os.Bundle;
import android.text.TextUtils; // Thêm import cho TextUtils (tùy chọn, để kiểm tra trống)
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;
    Button buttonLogin;
    TextView textViewRegisterLink;

    // Xóa hoặc bình luận các hằng số cũ này
    // private final String CORRECT_USERNAME = "admin";
    // private final String CORRECT_PASSWORD = "123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewRegisterLink = findViewById(R.id.textViewRegisterLink);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredUsername = editTextUsername.getText().toString().trim();
                String enteredPassword = editTextPassword.getText().toString().trim();

                // Kiểm tra trống (tùy chọn nhưng nên có)
                if (TextUtils.isEmpty(enteredUsername)) {
                    editTextUsername.setError("Tên đăng nhập không được để trống");
                    editTextUsername.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(enteredPassword)) {
                    editTextPassword.setError("Mật khẩu không được để trống");
                    editTextPassword.requestFocus();
                    return;
                }

                // Đọc thông tin đã lưu từ SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences(RegisterActivity.SHARED_PREFS, MODE_PRIVATE);
                String savedUsername = sharedPreferences.getString(RegisterActivity.KEY_USERNAME, null);
                String savedPassword = sharedPreferences.getString(RegisterActivity.KEY_PASSWORD, null);

                // Kiểm tra xem có tài khoản nào được đăng ký chưa
                if (savedUsername == null || savedPassword == null) {
                    Toast.makeText(LoginActivity.this, "Chưa có tài khoản nào được đăng ký. Vui lòng đăng ký trước.", Toast.LENGTH_LONG).show();
                    return;
                }

                // So sánh thông tin nhập vào với thông tin đã lưu
                if (enteredUsername.equals(savedUsername) && enteredPassword.equals(savedPassword)) {
                    // Đăng nhập thành công
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    // Xóa các activity trước đó khỏi stack để người dùng không quay lại màn hình đăng nhập
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish(); // Đóng LoginActivity
                } else {
                    // Đăng nhập thất bại
                    Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textViewRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}