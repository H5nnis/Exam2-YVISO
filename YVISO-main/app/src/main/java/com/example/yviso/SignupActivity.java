package com.example.yviso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import com.example.yviso.databinding.ActivitySignupBinding;


public class SignupActivity extends AppCompatActivity{
    ActivitySignupBinding binding;
    MyDatabase myDb;
    UserDao userDao;
    public static boolean isAllowed=false;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding=ActivitySignupBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        // Database and UserDao initialized.
        myDb= Room.databaseBuilder(this, MyDatabase.class, "usertable")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        userDao=myDb.getDao();

        // Listener for changes in the text for username, tells user if username is available before pressing sign up.
        binding.username.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){
            }
            @Override
            public void afterTextChanged(Editable s){
                String userName=s.toString();
                if (userDao.is_taken(userName)){
                    isAllowed=false;
                    Toast.makeText(SignupActivity.this, "In use", Toast.LENGTH_SHORT).show();
                }
                else {
                    isAllowed=true;
                }
            }
        });


        // Signup listener, toast with Created or Username is taken text.
        binding.signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // If available add to database
                if (isAllowed && !userDao.is_taken(binding.username.getText().toString())){
                    UserTable userTable=new UserTable(0, binding.username.getText().toString(),
                            binding.password.getText().toString());
                    userDao.insertUser(userTable);
                    Toast.makeText(SignupActivity.this, "Created", Toast.LENGTH_SHORT).show();
                }
                // Does not add to database
                else{
                    Toast.makeText(SignupActivity.this, "Username is already taken", Toast.LENGTH_LONG).show();
                }
            }
        });


        // Moves user to Login activity/page
        binding.goToLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
    }
}
