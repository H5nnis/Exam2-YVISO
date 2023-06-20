package com.example.yviso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import android.content.SharedPreferences;
import com.example.yviso.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity{
    ActivityLoginBinding binding;
    MyDatabase myDb;
    UserDao userDao;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        // Database and UserDao initialized.
        myDb= Room.databaseBuilder(this, MyDatabase.class, "usertable")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        userDao=myDb.getDao();

        // Starting the SharedPreferences for login, saving the login information.
        sharedPreferences = getSharedPreferences("MySaves", MODE_PRIVATE);
        boolean saveLoginInfo = sharedPreferences.getBoolean("saveLoginInfo", false);
        String savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");

        // Information + checkbox set.
        binding.checkbox.setChecked(saveLoginInfo);
        binding.username.setText(savedUsername);
        binding.password.setText(savedPassword);

        // OnClick listener for login.
        binding.login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String userName=binding.username.getText().toString();
                String password=binding.password.getText().toString();
                boolean saveLoginInfo = binding.checkbox.isChecked();
                SharedPreferences.Editor editor = sharedPreferences.edit();

                // If checkbox is checked the information will be saved.
                if (saveLoginInfo){
                    editor.putBoolean("saveLoginInfo", true);
                    editor.putString("username", userName);
                    editor.putString("password", password);
                    editor.apply();

                  // When checkbox is not checked the login information will not be saved for next time.
                } else{
                    editor.putBoolean("saveLoginInfo", false);
                    editor.remove("username");
                    editor.remove("password");
                }
                editor.apply();

                // Logs in user if verification is ok.
                if (userDao.login(userName, password)){
                    startActivity(new Intent(LoginActivity.this, Home.class));
                }

                // Tells user that verification is not ok, login will not happen.
                else{
                    Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}