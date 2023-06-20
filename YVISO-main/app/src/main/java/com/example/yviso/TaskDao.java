package com.example.yviso;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface TaskDao{

    // Get all tasks from the database
    @Query("SELECT * FROM task")
    List<Task> getAll();

    // Add new task to database
    @Insert
    void insert(Task task);

    // Deletes task from database
    @Delete
    void delete(Task task);

    // Updates/changes task in database
    @Update
    void update(Task task);
}