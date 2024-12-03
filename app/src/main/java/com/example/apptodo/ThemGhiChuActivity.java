package com.example.apptodo;

import static android.app.PendingIntent.getActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;



import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.apptodo.databinding.ThemghichuBinding;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;

public class ThemGhiChuActivity extends AppCompatActivity {
    // Your Activity or Fragment
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri; // Để lưu trữ URI của hình ảnh đã chọn
    int initialColor = Color.WHITE;
    private int day,month,year,hour,minutes;
    ThemghichuBinding binding;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ThemghichuBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
//
//        // Giả sử bạn có một danh sách ghi chú
//        List<NoteEntity> noteList = new ArrayList<>(); // Thêm ghi chú vào đây
//        NoteAdapter noteAdapter = new NoteAdapter(noteList,this); // Khởi tạo Adapter với danh sách ghi chú

       //dialog date
        binding.datePicker.setOnClickListener(v->{
            DatePickerDialog datePickerDialog=new DatePickerDialog(this);
            datePickerDialog.show();
            datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
                binding.datePicker.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                day=dayOfMonth;
                this.month=month;
                this.year=year;
            });
        });
        //dialog thời gian
        binding.starttime.setOnClickListener(v->{
            final Calendar c = Calendar.getInstance();
            int hour2 = c.get(Calendar.HOUR_OF_DAY);
            int minute2 = c.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog=new TimePickerDialog(ThemGhiChuActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String formattedTime = String.format("%02d:%02d", hourOfDay, minute);
                    binding.starttime.setText(formattedTime);
                    hour=hourOfDay;
                    minutes=minute;
                }
            },hour2,minute2,true);
            timePickerDialog.show();
        });
        // them ghi chú vào item
        binding.addGhiChu.setOnClickListener(v -> {
            // Lấy dữ liệu từ các trường nhập
            String title = binding.title.getText().toString().trim();
            String description = binding.moTa.getText().toString().trim();
            String date = binding.datePicker.getText().toString().trim();
            String starttime = binding.starttime.getText().toString().trim();

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day, hour, minutes, 0);
            long triggerAtMillis = calendar.getTimeInMillis();
            scheduleTaskReminder(this, triggerAtMillis, "Nhiệm vụ đến hạn!");
            // Kiểm tra nếu người dùng chưa nhập đủ dữ liệu

            if (title.isEmpty() || description.isEmpty() || date.isEmpty() || starttime.isEmpty()) {
                Toast.makeText(ThemGhiChuActivity.this, "Vui lòng nhập đầy đủ thông tin ghi chú", Toast.LENGTH_SHORT).show();
                return;
            }
            // Tạo Intent để gửi dữ liệu về HomeFragment
//            Intent resultIntent = new Intent();
//            resultIntent.putExtra("title", title);
//            resultIntent.putExtra("description", description);
//            resultIntent.putExtra("date", date);
//            resultIntent.putExtra("starttime", starttime);

            // Đặt kết quả và kết thúc Activity
//            setResult(RESULT_OK, resultIntent);
            new Thread(() -> {
                try{
                NoteEntity note = new NoteEntity();
                note.setDescription(description);
                note.setTitle(title);
                note.setDate(date);
                note.setStarttime(starttime);
                note.setColor(initialColor);
                UserDatabase.getUserDatabase(this).noteDAO().insertNote(note);
                runOnUiThread(() -> {
                    Toast.makeText(ThemGhiChuActivity.this, "Ghi chú đã được lưu", Toast.LENGTH_SHORT).show();
                    finish();
                });
                }
                catch (Exception e){
                }
            }).start();
            // Thông báo thành công
        });
        //hiển thị thời gian hiện tại
        displayRealTime();


       //Đổi màu giao diện
        binding.color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmbilWarnaDialog dialog = new AmbilWarnaDialog(
                        ThemGhiChuActivity.this, initialColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        // color is the color selected by the user.
                        binding.Themghichu.setBackgroundColor(color);
                        // Lưu màu đã chọn
                        initialColor = color;
                        // Cập nhật màu cho RecyclerView
                        //NoteAdapter.setItemColor(itemColor);
                    }
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                        // cancel was selected by the user
                    }
                });
                dialog.show();
            }
        });
        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage(); // Gọi phương thức chọn hình ảnh khi click vào ImageView
            }
        });
    }
    // Thời gian hiện tại trong thêm ghi chú
    private void displayRealTime() {
        // Lấy và hiển thị thời gian hiện tại
        Calendar calendar = Calendar.getInstance();
        String currentTime = DateFormat.format("HH:mm", calendar).toString();
        binding.realTime.setText(currentTime);
    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Nhận kết quả từ hộp thoại chọn hình
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            // Hiển thị hình ảnh bằng Glide
            ImageView imageView = findViewById(R.id.img);
            Glide.with(this).load(imageUri).into(imageView);
        }
    }

    public void scheduleTaskReminder(Context context, long triggerAtMillis, String taskTitle) {
        Intent intent = new Intent(context, TaskReminderReceiver.class);
        intent.putExtra("task_title", taskTitle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        }
    }
    
}





