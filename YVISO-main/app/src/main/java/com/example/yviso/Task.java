package com.example.yviso;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity
public class Task implements Serializable{

    //Column in entity task. Ex task column, desc column etc. Stores in columns.
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "task")
    private String task;

    @ColumnInfo(name = "desc")
    private String desc;

    @ColumnInfo(name = "checkMot")
    private boolean checkMot;

    @ColumnInfo(name = "checkService")
    private boolean checkService;

    @ColumnInfo(name = "details")
    private String details;

    @ColumnInfo(name = "date")
    private String date;



    // Get ID for vehicle/task
    public int getId(){
        return id;
    }
    // Set ID for vehicle/task
    public void setId(int id){
        this.id = id;
    }


    //  Get license plate of vehicle
    public String getTask(){
        return task;
    }
    // Set license plate of vehicle
    public void setTask(String task){
        this.task = task;
    }


    //  Get brand of vehicle
    public String getDesc(){
        return desc;
    }
    // Set brand of vehicle
    public void setDesc(String desc){
        this.desc = desc;
    }


    //  Get checkbox MOT / Inspection
    public boolean isCheckMot(){
        return checkMot;
    }
    // Set checkbox MOT / Inspection
    public void setCheckMot(boolean checkMot){
        this.checkMot = checkMot;
    }


    // Get checkbox Service
    public boolean isCheckService(){
        return checkService;
    }
    // Set checkbox Service
    public void setCheckService(boolean checkService){
        this.checkService = checkService;
    }


    // Get edit field with notes/details
    public String getDetails(){
        return details;
    }
    // Set edit field with notes/details
    public void setDetails(String details){
        this.details = details;
    }


    // Get calendar and Date fields
    public String getDate(){
        return date;
    }
    // Set calendar and Date fields
    public void setDate(String date){
        this.date = date;
    }
}

