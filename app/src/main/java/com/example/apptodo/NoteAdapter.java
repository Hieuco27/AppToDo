package com.example.apptodo;



import static androidx.core.content.ContentProviderCompat.requireContext;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptodo.databinding.ItemAddNoteBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import yuku.ambilwarna.AmbilWarnaDialog;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private final Context context;
    private int itemColor = Color.WHITE;


    private List<NoteEntity> noteList;
    private List<NoteEntity> filteredList; // Danh sách đã lọc

    public void updateData(List<NoteEntity> newItemList) {
        this.noteList=newItemList;
        this.filteredList=noteList;
        notifyDataSetChanged(); // Thông báo cho adapter rằng dữ liệu đã thay đổi
    }
    // Constructor để khởi tạo adapter với danh sách ghi chú
    public NoteAdapter( Context context) {

        this.context = context;

    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAddNoteBinding binding = ItemAddNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NoteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        NoteEntity note = noteList.get(position);
        if(note == null) {
            return;
        }
        // Gán giá trị cho tiêu đề và mô tả từ NoteEntity
        holder.binding.tvTaskTitle.setText(note.getTitle());
        holder.binding.tvTaskDescription.setText(note.getDescription());
        holder.binding.tvDate.setText(note.getDate());
        holder.binding.tvStartTime.setText(note.getStarttime());
        holder.binding.pin.setTextDirection(note.getIsPinned());
        holder.binding.lnCardTask.setBackgroundColor(note.getColor());

        holder.itemView.setBackgroundColor(note.getColor());

        // Thêm sự kiện OnLongClickListener
        holder.itemView.setOnLongClickListener(view -> {
            showPopupMenu(view, note);
            return true; // Trả về true để chặn sự kiện click mặc định
        });
        holder.binding.cardTask.setOnClickListener(view -> {
            showEditDialog(view, note);
        });
    }

    public void updateItemColor(int position, int color) {
        // Cập nhật màu cho item tại vị trí position
        noteList.get(position).setColor(color);
        notifyItemChanged(position); // Gọi lại để cập nhật item trong RecyclerView
    }
   //Hàm sửa ghi chú
    private void showEditDialog(View view, NoteEntity note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.themghichu, null);
        builder.setView(dialogView);
        // Khởi tạo các thành phần trong dialog
        EditText Title = dialogView.findViewById(R.id.title);
        EditText Description = dialogView.findViewById(R.id.mo_ta);
        TextView Date = dialogView.findViewById(R.id.date_picker);
        TextView StartTime = dialogView.findViewById(R.id.starttime);
        TextView Save = dialogView.findViewById(R.id.addGhiChu);
        LinearLayoutCompat tgc=dialogView.findViewById(R.id.Themghichu);
        ImageView imgc=dialogView.findViewById(R.id.color);
        ImageView edit= dialogView.findViewById(R.id.edit);

        imgc.setOnClickListener(v -> {
            AmbilWarnaDialog dialog = new AmbilWarnaDialog(
                    view.getContext(), Color.WHITE, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onOk(AmbilWarnaDialog dialog, int color) {
                    // color is the color selected by the user.
                    tgc.setBackgroundColor(color);
                    // Lưu màu đã chọn
                    note.setColor(color);
                    // Cập nhật màu cho RecyclerView
                    //NoteAdapter.setItemColor(itemColor);
                }
                @Override
                public void onCancel(AmbilWarnaDialog dialog) {
                    // cancel was selected by the user
                }
            });
            dialog.show();
        });
        // Đặt giá trị cho các thành phần
        Title.setText(note.getTitle());
        Description.setText(note.getDescription());
        Date.setText(note.getDate());
        StartTime.setText(note.getStarttime());
        tgc.setBackgroundColor(note.getColor());
        // Hiển thị dialog
        AlertDialog dialog = builder.create();
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        Save.setOnClickListener(v -> {
            // Cập nhật giá trị cho ghi chú hiện tại
            note.setTitle(Title.getText().toString());
            note.setDescription(Description.getText().toString());
            note.setDate(Date.getText().toString());
            note.setStarttime(StartTime.getText().toString());

            // Cập nhật ghi chú vào cơ sở dữ liệu
            updateNoteInDatabase(note);
            // Cập nhật danh sách hiển thị
            int position = noteList.indexOf(note); // Lấy vị trí ghi chú hiện tại
            notifyItemChanged(position); // Cập nhật item trong RecyclerView
            dialog.dismiss(); // Đóng dialog
        });
        dialog.show();
    }
    // Hàm cập nhật ghi chú vào cơ sở dữ liệu
    public void updateNoteInDatabase(NoteEntity note) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            NoteDAO noteDAO = UserDatabase.getUserDatabase(context).noteDAO();
            noteDAO.updateNote(note); // Cập nhật ghi chú vào cơ sở dữ liệu
        });
    }

    // Hàm thêm ghi chú vào danh sách và vào cơ sở dữ liệu
    public void addNote(NoteEntity note) {
        // Thêm ghi chú vào danh sách
        noteList.add(note);
        notifyItemInserted(noteList.size() - 1); // Cập nhật adapter
        // Thêm ghi chú vào cơ sở dữ liệu
        addNoteToDatabase(note);
    }

    // Hàm cập nhật ghi chú đã thay đổi vào cơ sở dữ liệu
    private void addNoteToDatabase(NoteEntity note) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            NoteDAO noteDAO = UserDatabase.getUserDatabase(context).noteDAO();
            noteDAO.insertNote(note); // Chèn ghi chú vào cơ sở dữ liệu
        });
    }
   // Hiển thị popup menu
    private void showPopupMenu(View view, NoteEntity note) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menupopup, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.xoa:
                    // Xử lý sự kiện xóa
                    deleteNote(note);
                    return true;
                case R.id.ghim:
                    // Xử lý sự kiện ghim
                    pinNote(note);
                    return true;
                default:
                    return false;
            }
        });
        popupMenu.show();
    }
    // Xóa ghi chú
    private void deleteNote(NoteEntity note) {
        noteList.remove(note);
        notifyDataSetChanged(); // Cập nhật RecyclerView
        // Cập nhật cơ sở dữ liệu
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            NoteDAO noteDAO = UserDatabase.getUserDatabase(context).noteDAO();
            noteDAO.deleteNote(note); // Xóa ghi chú khỏi cơ sở dữ liệu
        });
    }
    private void pinNote(NoteEntity note) {
    }
    @Override
    public int getItemCount() {

        return (noteList != null) ?
                noteList.size() : 0;
    }
    // Hàm lọc ghi chú
    public void filter(String query) {
        if (query.isEmpty()) {
            noteList = new ArrayList<>(filteredList); // Hiển thị lại danh sách gốc
        } else {
            noteList = new ArrayList<>();
            for (NoteEntity note : filteredList) {
                if (note.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    noteList.add(note); // Lọc các ghi chú chứa từ khóa
                }
            }
        }
        notifyDataSetChanged(); // Cập nhật RecyclerView
    }
    // Inner class để tạo ViewHolder cho từng item
    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        private final ItemAddNoteBinding binding;

        public NoteViewHolder(@NonNull ItemAddNoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}