package com.example.yviso;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao{
    @Insert
    // Add new user to database.
    void insertUser(UserTable userTable);

    // Check if username is taken.
    @Query("SELECT EXISTS (SELECT*from UserTable where userName=:userName)")
    public boolean is_taken(String userName);

    // Login verification.
    @Query("SELECT EXISTS (SELECT*from UserTable where userName=:userName AND password=:password)")
    boolean login(String userName, String password);
}