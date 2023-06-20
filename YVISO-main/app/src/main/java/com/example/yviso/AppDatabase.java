package com.example.yviso;

import androidx.room.Database;
import androidx.room.RoomDatabase;

// Room database. Access to taskDao object.
@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    public abstract TaskDao taskDao();
}