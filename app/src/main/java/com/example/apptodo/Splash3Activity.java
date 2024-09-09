package com.example.apptodo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Splash3Activity extends AppCompatActivity {
    ImageView imgArr;
    AppCompatButton btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash3);
        imgArr = findViewById(R.id.imgArr);

        btnLogin = findViewById(R.id.btnLogin);

        imgArr.setOnClickListener(view -> {
            finish();
        });
        btnLogin.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }
}