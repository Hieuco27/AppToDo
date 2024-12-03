package com.example.apptodo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptodo.databinding.HomefragmentBinding;
import com.example.apptodo.databinding.ProfileFragmentBinding;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    ProfileFragmentBinding binding;
    private TextView currentNameTextView;

    // Biến để lưu trữ TextView hiện tại

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ProfileFragmentBinding.inflate(getLayoutInflater());
        currentNameTextView = binding.rename; // Giả sử đây là TextView bạn muốn cập nhật


        TextView doiMkTextView = binding.doiMk;
        TextView setting = binding.setting;
        TextView rename = binding.rename;
        CircleImageView anh=binding.img;

        binding.doiMk.setOnClickListener(v -> showChangePasswordDialog());
        binding.rename.setOnClickListener(v -> showRenameDialog());
        return binding.getRoot();
    }



    private void showChangePasswordDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.profile_changepassword);
        dialog.show();
    }

    private void showRenameDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.rename, null);

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(dialogView)
                .setCancelable(true)
                .create();

        EditText editTextNewName = dialogView.findViewById(R.id.newrename);
        Button buttonRename = dialogView.findViewById(R.id.buttonRename);

        buttonRename.setOnClickListener(v -> {
            String newName = editTextNewName.getText().toString().trim();
            if (!newName.isEmpty()) {
                // Cập nhật TextView với tên mới
                currentNameTextView.setText(newName); // Cập nhật TextView
                dialog.dismiss();
            } else {
                Toast.makeText(getContext(), "Vui lòng nhập tên mới", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

}
