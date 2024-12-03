package com.example.apptodo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "users")
public class UserEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "password")
    String password;
    @ColumnInfo(name = "cpassword")
    String cpassword;
    @ColumnInfo(name="name")
    String name;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCpassword(String cpassword) {
        this.cpassword = cpassword;
    }
    public String getCpassword() {
        return cpassword;
    }

}
