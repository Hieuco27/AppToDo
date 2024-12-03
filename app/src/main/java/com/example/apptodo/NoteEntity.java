package com.example.apptodo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class NoteEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private String date;
    private String starttime;
    private int color;
    private String imageUri;

    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    private boolean isPinned; // Trạng thái ghi chú có ghim hay không

    // Constructor, Getter, và Setter
    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public NoteEntity() {
    }

    public NoteEntity(String date, String description, String starttime, String title, String imageUri, int color) {
        this.date = date;
        this.description = description;
        this.starttime = starttime;
        this.title = title;
        this.imageUri = imageUri;
        this.color=color;

    }


    public int getIsPinned() {


        return 0;
    }
}
