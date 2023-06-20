package com.example.yviso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity{

    private EditText editTextTask, editTextDesc, editTextDetails;
    private CheckBox checkMotUpdate, checkServiceUpdate;
    private CalendarView calendarview;
    String currentDate;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Fetch text fields.
        editTextTask = findViewById(R.id.editTextTask);
        editTextDesc = findViewById(R.id.editTextDesc);
        editTextDetails = findViewById(R.id.editDetails);

        // Checkboxes.
        checkMotUpdate = findViewById(R.id.checkMotUpdate);
        checkServiceUpdate = findViewById(R.id.checkServiceUpdate);

        // Fetch date / calendar.
        calendarview = findViewById(R.id.calendarView);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = sdf.format(calendar.getTime());

        // Move from add task to homepage.
        findViewById(R.id.backbutton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(AddTaskActivity.this, Home.class));
            }
        });

        // Set up a listener for onClick for the save button.
        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                saveTask();
            }
        });

        // Set up a onDateChange Listener for change day on calendar.
        calendarview.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day){
                month++;
                currentDate = year + "-" + month +  "-" + day;
            }
        });
    }


    // Function to save created tasks.
    private void saveTask(){
        final String sTask = editTextTask.getText().toString().trim();
        final String sDesc = editTextDesc.getText().toString().trim();
        final String sDetails = editTextDetails.getText().toString().trim();
        final String sDate = currentDate;
        final boolean isCheckedMot = checkMotUpdate.isChecked();
        final boolean isCheckedService = checkServiceUpdate.isChecked();

        // Requests that the user adds text to the field.
        if (sTask.isEmpty()){
            editTextTask.setError("License plate required");
            editTextTask.requestFocus();
            return;
        }

        // Requests that the user adds text to the field.
        if (sDesc.isEmpty()){
            editTextDesc.setError("Brand required");
            editTextDesc.requestFocus();
            return;
        }

        // AsyncTask to save created task to the database. Happens in the background.
        class SaveTask extends AsyncTask<Void, Void, Void>{
            @Override
            protected Void doInBackground(Void... voids){
                // Creating a task.
                Task task = new Task();
                task.setTask(sTask);
                task.setDesc(sDesc);
                task.setDetails(sDetails);
                task.setCheckMot(isCheckedMot);
                task.setCheckService(isCheckedService);
                task.setDate(sDate);

                // Adding to database.
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .taskDao()
                        .insert(task);
                return null;
            }

            // After SaveTask is done takes user to Home. Also shows toast message that task is saved.
            @Override
            protected void onPostExecute(Void aVoid){
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), Home.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        // Makes instance of SaveTask and runs/executes it.
        SaveTask st = new SaveTask();
        st.execute();
    }
}
