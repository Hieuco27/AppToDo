package com.example.apptodo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.apptodo.databinding.DanhmucNoteBinding;

public class DanhMucActivity extends AppCompatActivity {
    DanhmucNoteBinding binding;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DanhmucNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        inflater = LayoutInflater.from(this);
        // Đặt menuItem1 ẩn khi bắt đầu


        // Sự kiện cho nút "back"
        binding.back.setOnClickListener(v -> finish());

        // Sự kiện cho nút "Thêm mục"
        binding.add.setOnClickListener(v -> {
            addMenuItem();
        });

        /*// Sự kiện cho nút "Clear"
        binding.clear.setOnClickListener(v -> {
            // Lấy EditText từ menuItem1
            EditText editText = binding.text; // Giả sử editText có id là "text"
            // Xóa nội dung trong EditText
            editText.setText("");
        });*/
    }

    private void addMenuItem() {
        // Tạo LayoutInflater và nạp layout cho mỗi mục mới
        View menuItem = inflater.inflate(R.layout.item_danhmuc, null, false);

        // Lấy EditText và các view bên trong menuItem mới
        EditText newItemEditText = menuItem.findViewById(R.id.text);
        ImageView clearImageView = menuItem.findViewById(R.id.clear);

        // Thiết lập sự kiện cho biểu tượng Clear của mục mới
        clearImageView.setOnClickListener(v -> {
            // Xóa menuItem này khỏi menuContainer
            binding.menuContainer.removeView(menuItem);
        });
        // Thêm menuItem vào menuContainer
        binding.menuContainer.addView(menuItem);
    }
}
