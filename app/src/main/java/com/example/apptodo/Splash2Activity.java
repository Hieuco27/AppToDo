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

public class Splash2Activity extends AppCompatActivity {
    TextView tvSkip,tvBack;
    AppCompatButton btnnext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.onboadding2);
        tvSkip=findViewById(R.id.tvSkip);
        tvBack=findViewById(R.id.tvBack);
        btnnext=findViewById(R.id.btnnext);
        tvSkip.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

        });
        btnnext.setOnClickListener(view -> {
            Intent intent = new Intent(this, Splash3Activity.class);
            startActivity(intent);
        });
        tvBack.setOnClickListener(view->{
            finish();
        });
    }
}