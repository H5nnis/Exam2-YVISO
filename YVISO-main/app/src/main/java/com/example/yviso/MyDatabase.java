package com.example.yviso;

import androidx.room.Database;
import androidx.room.RoomDatabase;

// Room database, specify just 1 entity
@Database(entities = {UserTable.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase{
    public abstract UserDao getDao();
}