package com.example.apptodo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {UserEntity.class, NoteEntity.class}, version = 2)
public abstract class UserDatabase extends RoomDatabase {

    private static final String DB_NAME = "note.db"; // Sử dụng một tên duy nhất cho DB

    private static UserDatabase userDatabase;

    public static synchronized UserDatabase getUserDatabase(Context context) {
        if (userDatabase == null) {
            userDatabase = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    //.addMigrations(MIGRATION_1_2) // Thêm migration nếu cần// Tùy chọn nếu bạn muốn xóa dữ liệu cũ khi thay đổi cấu trúc
                    .build();
        }
        return userDatabase;
    }

    public abstract UserDAO userDAO();
    public abstract NoteDAO noteDAO();
}

