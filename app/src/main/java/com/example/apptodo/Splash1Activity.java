package com.example.apptodo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Splash1Activity extends AppCompatActivity {
    TextView tvSkip;
    AppCompatButton btnnext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash1);
        tvSkip=findViewById(R.id.tvSkip);
        btnnext=findViewById(R.id.btnnext);
        tvSkip.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

        });
        btnnext.setOnClickListener(view -> {
            Intent intent = new Intent(this, Splash2Activity.class);
            startActivity(intent);
        });

    }
}