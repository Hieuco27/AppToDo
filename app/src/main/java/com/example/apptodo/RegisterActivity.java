package com.example.apptodo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    LinearLayout llogin;
    EditText edtUsername1, edtPassword1, edtCPassword;
    Button btnRegister;
    ImageView imgArrlogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        // Khởi tạo các thành phần UI
        llogin = findViewById(R.id.lllogin);
        edtUsername1 = findViewById(R.id.edtUsername);
        edtPassword1 = findViewById(R.id.edtPassword);
        edtCPassword = findViewById(R.id.edtCPassword);
        btnRegister = findViewById(R.id.btnRegister1);
        imgArrlogin = findViewById(R.id.imgArrlogin);


        // Sự kiện click cho LinearLayout để quay lại trang đăng nhập
        llogin.setOnClickListener(view -> {
//            finish();
            onBackPressed();// Đóng Activity hiện tại
        });
        imgArrlogin.setOnClickListener(view -> {
            finish();
                });

        // Sự kiện click cho nút đăng ký
        btnRegister.setOnClickListener(view -> {
            String username = edtUsername1.getText().toString().trim();
            String password = edtPassword1.getText().toString().trim();
            String confirmPassword = edtCPassword.getText().toString().trim();

            // Kiểm tra thông tin đăng ký
            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(RegisterActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            } else {
                // Thực hiện đăng ký (bạn có thể lưu thông tin vào cơ sở dữ liệu
                registerUser(username, password);
            }
        });
    }

    // Phương thức giả định để thực hiện đăng ký
    private void registerUser(String username, String password) {
        // Logic để lưu thông tin người dùng (ví dụ: gọi API, lưu vào cơ sở dữ liệu)
        UserEntity us=new UserEntity();
        us.setName(username);
        us.setPassword(password);

        new Thread(() -> {
            try {
                UserDatabase DB = UserDatabase.getUserDatabase(RegisterActivity.this);
                DB.userDAO().registerU(us);
                runOnUiThread(() -> {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                });
            }
            catch (Exception e)
            {
                runOnUiThread(() -> {
                    Toast.makeText(RegisterActivity.this, "Lỗi gì rồi", Toast.LENGTH_SHORT).show();
                });
            }


        }).start();
        finish(); // Đóng Activity sau khi đăng ký thành công
    }
}