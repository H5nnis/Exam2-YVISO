package com.example.yviso;

import androidx.room.Room;
import android.content.Context;

public class DatabaseClient{

    private Context mCtx;
    private static DatabaseClient mInstance;
    // Database object.
    private AppDatabase appDatabase;


    private DatabaseClient(Context mCtx){
        this.mCtx = mCtx;

        // Database (MyVehicles) created with Room database builder.
        appDatabase = Room.databaseBuilder(mCtx, AppDatabase.class, "MyVehicles").build();
    }


    // Get DatabaseClient instance. Creates if no instance, else return instance that is already existing.
    public static synchronized DatabaseClient getInstance(Context mCtx){
        if (mInstance == null){
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }


    // Gives access to the database operations.
    public AppDatabase getAppDatabase(){
        return appDatabase;
    }
}
