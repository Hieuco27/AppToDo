package com.example.apptodo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UserDAO {
    @Insert
    void registerU(UserEntity userEntity);

    @Query("SELECT * FROM users WHERE name=:name AND password=:password")
    UserEntity getUserDatabase(String name, String password);


}



