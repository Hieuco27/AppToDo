package com.example.apptodo;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.example.apptodo.databinding.HomefragmentBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    private HomefragmentBinding mHomefragmentBinding;
    private NoteAdapter noteAdapter;
    private List<NoteEntity> noteList = new ArrayList<>();
    private UserDatabase userDatabase; // Biến toàn cục cho UserDatabase

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout và gán binding
        mHomefragmentBinding = HomefragmentBinding.inflate(inflater, container, false);
        userDatabase=UserDatabase.getUserDatabase(getContext());
        noteAdapter = new NoteAdapter(requireContext());

        loadNotesFromDatabase(); // Lấy dữ liệu từ DB

        // Thiết lập RecyclerView và adapter
        RecyclerView recyclerView = mHomefragmentBinding.rcv;
        int numberOfColumns = 2; // Số cột bạn muốn hiển thị
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), numberOfColumns));
        recyclerView.setAdapter(noteAdapter);

        // Lấy dữ liệu từ database và cập nhật noteList trong adapter

        // Xử lý sự kiện khi nhấn nút thêm ghi chú
        mHomefragmentBinding.addNote.setOnClickListener(v -> openThemGhiChuActivity());
        mHomefragmentBinding.danhmuc.setOnClickListener(v -> openDanhMucActivity());
        mHomefragmentBinding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                noteAdapter.filter(s.toString()); // Gọi hàm filter trong Adapter
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
       /* mHomefragmentBinding.imgSearch.setOnClickListener(v -> {
            String searchText = mHomefragmentBinding.search.getText().toString();
            noteAdapter.filter(searchText);
        });*/
        /*mHomefragmentBinding.imgSearch.setOnClickListener(v -> {
            String searchText = mHomefragmentBinding.search.getText().toString();

            new Thread(() -> {
                List<NoteEntity> resultList;

                if (TextUtils.isEmpty(searchText)) {
                    resultList = userDatabase.noteDAO().getAllNotes();  // Lấy tất cả ghi chú nếu ô tìm kiếm trống
                } else {
                    resultList = userDatabase.noteDAO().getSearchNotes("%" + searchText + "%"); // Tìm kiếm theo từ khóa
                }

                // Cập nhật giao diện trên luồng chính
                requireActivity().runOnUiThread(() -> {
                    noteAdapter.updateData(resultList); // Cập nhật RecyclerView với danh sách kết quả
                });
            }).start();
        });*/

        mHomefragmentBinding.sortBy.setOnClickListener(v -> {
                 sortByMenu(v, requireContext());
               });
        // Trả về View gốc của Fragment
        return mHomefragmentBinding.getRoot();
    }

    private void openDanhMucActivity() {
        Intent intent=new Intent(getActivity(),DanhMucActivity.class);
       addNoteLauncher.launch(intent);

    }

    private void sortByMenu(View view, Context context) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.arrange_menupopup, popupMenu.getMenu()); // Đây là file menu bạn đã tạo

        popupMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.ten:
                    // Xử lý sự kiện sắp xếp theo tên
                    noteList.sort((note1, note2) -> note1.getTitle().compareToIgnoreCase(note2.getTitle()));
                    noteAdapter.notifyDataSetChanged();  // Cập nhật dữ liệu cho adapter
                    return true;
                case R.id.time:
                    // Xử lý sự kiện sắp xếp theo thời gian
                    noteList.sort((note1, note2) -> note1.getDate().compareTo(note2.getDate())); // Sắp xếp theo ngày
                    noteAdapter.notifyDataSetChanged();  // Cập nhật dữ liệu cho adapter
                    return true;
                default:
                    return false;
            }
        });
        popupMenu.show(); // Hiển thị PopupMenu
    }
    private void loadNotesFromDatabase() {
        new Thread(() -> {
            noteList = userDatabase.noteDAO().getAllNotes();
            requireActivity().runOnUiThread(() -> {
                noteAdapter.updateData(noteList);
            });
        }).start();
    }

    private void openThemGhiChuActivity() {
        Intent intent = new Intent(getActivity(), ThemGhiChuActivity.class);
        addNoteLauncher.launch(intent);
    }

    // Đăng ký ActivityResultLauncher để nhận dữ liệu từ ThemGhiChuActivity
    private final ActivityResultLauncher<Intent> addNoteLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                        loadNotesFromDatabase();
                }
            }
    );

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomefragmentBinding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadNotesFromDatabase();
    }
}