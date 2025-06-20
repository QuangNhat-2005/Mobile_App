package com.example.caoquangnhat_2123110077; // Thay thế bằng tên package của bạn

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button; // Hoặc com.google.android.material.button.MaterialButton nếu bạn dùng trực tiếp
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText editTextRegUsername, editTextRegPassword, editTextRegConfirmPassword;
    Button buttonRegister; // Sử dụng Button hoặc MaterialButton tùy theo cách bạn khai báo trong XML
    // Nếu trong XML là com.google.android.material.button.MaterialButton thì ở đây nên là MaterialButton

    // Tên cho SharedPreferences file và các keys
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextRegUsername = findViewById(R.id.editTextRegUsername);
        editTextRegPassword = findViewById(R.id.editTextRegPassword);
        editTextRegConfirmPassword = findViewById(R.id.editTextRegConfirmPassword);
        buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextRegUsername.getText().toString().trim();
                String password = editTextRegPassword.getText().toString().trim();
                String confirmPassword = editTextRegConfirmPassword.getText().toString().trim();

                // Kiểm tra tính hợp lệ
                if (TextUtils.isEmpty(username)) {
                    editTextRegUsername.setError("Tên đăng nhập không được để trống");
                    editTextRegUsername.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    editTextRegPassword.setError("Mật khẩu không được để trống");
                    editTextRegPassword.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(confirmPassword)) {
                    editTextRegConfirmPassword.setError("Xác nhận mật khẩu không được để trống");
                    editTextRegConfirmPassword.requestFocus();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    editTextRegConfirmPassword.setError("Mật khẩu xác nhận không khớp");
                    editTextRegConfirmPassword.requestFocus();
                    // Xóa nội dung ô xác nhận mật khẩu để người dùng nhập lại
                    editTextRegConfirmPassword.setText("");
                    return;
                }

                // Nếu tất cả đều hợp lệ, tiến hành lưu thông tin
                saveRegistrationData(username, password);

                Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

                // Chuyển sang HomeActivity
                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                // Xóa các activity trước đó khỏi stack để người dùng không quay lại màn hình đăng ký/đăng nhập
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish(); // Đóng RegisterActivity
            }
        });
    }

    private void saveRegistrationData(String username, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password); // Lưu ý: Trong thực tế không nên lưu mật khẩu dạng plain text
        editor.apply(); // Sử dụng apply() để lưu bất đồng bộ và nhanh hơn commit()
    }
}