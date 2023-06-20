package com.example.yviso;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "usertable")
public class UserTable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userName;
    private String password;

    // Get ID.
    public int getId(){
        return id;
    }
    // Set ID.
    public void setId(int id){
        this.id = id;
    }


    // Get username.
    public String getUserName(){
        return userName;
    }
    // Set username.
    public void setUserName(String userName){
        this.userName = userName;
    }


    // Get password.
    public String getPassword(){
        return password;
    }
    // Set password.
    public void setPassword(String password){
        this.password = password;
    }


    // UserTable class constructor, ID of user, username of user, password of user.
    public UserTable(int id, String userName, String password){
        this.id = id;
        this.userName = userName;
        this.password = password;
    }
}
