package com.example.apptodo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment; // Import Fragment
import androidx.fragment.app.FragmentManager; // Import FragmentManager
import androidx.fragment.app.FragmentTransaction; // Import FragmentTransaction
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptodo.databinding.MainActivityBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    MainActivityBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.addfab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ThemGhiChuActivity.class);
            startActivity(intent);
        });
        replaceFragment(new HomeFragment());

        binding.menuBottom.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.ic_home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.ic_profile:
                    replaceFragment(new ProfileFragment());
                    break;
            }
            return true;
        });

    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(binding.fragmentContainer.getId(),fragment);
        fragmentTransaction.commit();
    }
}
