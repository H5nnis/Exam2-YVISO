package com.example.yviso;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UpdateTaskActivity extends AppCompatActivity{

    // Text fields.
    private EditText editTextTask, editTextDesc, editTextDetails;

    // Checkboxes.
    private CheckBox checkServiceUpdate, checkMotUpdate;

    // Calendar and Date.
    String selectedDate;
    private CalendarView calendarview;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);
        // Fetch text fields.
        editTextTask = findViewById(R.id.editTextTask);
        editTextDesc = findViewById(R.id.editTextDesc);
        editTextDetails = findViewById(R.id.editDetails);
        // Checkboxes.
        checkMotUpdate = findViewById(R.id.checkMotUpdate);
        checkServiceUpdate = findViewById(R.id.checkServiceUpdate);
        // Fetch calendar.
        calendarview = findViewById(R.id.calendarView);

        final Task task = (Task) getIntent().getSerializableExtra("task");

        loadTask(task);


        // Button to go back to Homepage/screen.
        findViewById(R.id.backbutton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(UpdateTaskActivity.this, Home.class));
            }
        });


        // Set up a listener for onClick for the update task button.
        findViewById(R.id.button_update).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                updateTask(task);
            }
        });

        // Set up a OnDateChangeListener for change day on calendar.
        calendarview.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day){
                month++;
                selectedDate = year + "-" + month +  "-" + day;
            }
        });

        // Set up a listener for onClick for the delete button.
        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                // Alert for user asking if sure to delete.
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateTaskActivity.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    @Override
                    // Delete task.
                    public void onClick(DialogInterface dialogInterface, int i){
                        deleteTask(task);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
                    @Override
                    // Do not delete task.
                    public void onClick(DialogInterface dialogInterface, int i){
                    }
                });
                AlertDialog ad = builder.create();
                ad.show();
            }
        });
    }

    private void loadTask(Task task){
        editTextTask.setText(task.getTask());
        editTextDesc.setText(task.getDesc());
        editTextDetails.setText(task.getDetails());
        checkMotUpdate.setChecked(task.isCheckMot());
        checkServiceUpdate.setChecked(task.isCheckService());
        selectedDate = task.getDate();

        // Update calendar.
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            Date parsedDate = sdf.parse(task.getDate());
            calendar.setTime(parsedDate);
            calendarview.setDate(calendar.getTimeInMillis());
        } catch (ParseException e){
            Toast.makeText(this, "Error parsing date", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTask(final Task task){
        // Text fields.
        final String sTask = editTextTask.getText().toString().trim();
        final String sDesc = editTextDesc.getText().toString().trim();
        final String sDetails = editTextDetails.getText().toString().trim();

        // Checkboxes.
        final boolean isMotChecked = checkMotUpdate.isChecked();
        final boolean isServiceChecked = checkServiceUpdate.isChecked();

        // Date.
        final String sDate = selectedDate;

        // Text field for license plate.
        if (sTask.isEmpty()){
            editTextTask.setError("License plate required");
            editTextTask.requestFocus();
            return;
        }

        // Text field for brand.
        if (sDesc.isEmpty()){
            editTextDesc.setError("Brand of vehicle required");
            editTextDesc.requestFocus();
            return;
        }

        // Update tasks, text fields and checkboxes.
        class UpdateTask extends AsyncTask<Void, Void, Void>{

            @Override
            protected Void doInBackground(Void... voids){
                task.setTask(sTask);
                task.setDesc(sDesc);
                task.setDetails(sDetails);
                task.setCheckMot(isMotChecked);
                task.setCheckService(isServiceChecked);
                task.setDate(sDate);
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .taskDao()
                        .update(task);
                return null;
            }

            // After update make toast and return to homepage.
            @Override
            protected void onPostExecute(Void aVoid){
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(UpdateTaskActivity.this, Home.class));
            }
        }
        UpdateTask ut = new UpdateTask();
        ut.execute();
    }


    // Delete task from database.
    private void deleteTask(final Task task){
        class DeleteTask extends AsyncTask<Void, Void, Void>{
            @Override
            protected Void doInBackground(Void... voids){
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .taskDao()
                        .delete(task);
                return null;
            }

            // Notify task is deleted and return to homepage.
            @Override
            protected void onPostExecute(Void aVoid){
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(UpdateTaskActivity.this, Home.class));
            }
        }
        DeleteTask dt = new DeleteTask();
        dt.execute();
    }

}