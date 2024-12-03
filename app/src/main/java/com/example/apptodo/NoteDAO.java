package com.example.apptodo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.apptodo.NoteEntity;

import java.util.List;

@Dao
public interface NoteDAO {

    @Insert
    void insertNote(NoteEntity note );
    @Update
    void updateNote(NoteEntity note);
    @Delete
    void deleteNote(NoteEntity note);

    @Query("SELECT * FROM note_table ORDER BY id ASC")// sắp xếp id theo thứ tự tăng dần
    List<NoteEntity> getAllNotes();
    @Query("SELECT * FROM note_table Where title LIKE :title ")
    List<NoteEntity> getSearchNotes(String title);
}
