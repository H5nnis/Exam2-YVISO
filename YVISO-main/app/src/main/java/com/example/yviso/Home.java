package com.example.yviso;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity{
    private FloatingActionButton fabAddTask;
    private RecyclerView recyclerView;
    private List<Task> taskList;


    // Sets the homepage with recyclerview displaying items from the database.
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.recyclerview_tasks);
        fabAddTask = findViewById(R.id.fabAddTask);
        fabAddTask.setOnClickListener(new View.OnClickListener(){
            @Override
            // Start AddTaskActivity after clicking "add task" button displayed as a + sign
            public void onClick(View view){
                Intent intent = new Intent(Home.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });
        // Gets data from the database for the recyclerview
        taskList = new ArrayList<>();
        getTasks();
    }


    // Search function in the top bar with icon
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search plate here...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query){
                return false;
            }
            // Filtering based on what is searched, updating per character.
            @Override
            public boolean onQueryTextChange(String newText){
                filterTasks(newText);
                return false;
            }
        });
    return super.onCreateOptionsMenu(menu);
    }


    // Filtering tasks on what is written in search.
    private void filterTasks(String query){
        List<Task> filteredTasks = new ArrayList<>();

        for (Task task : taskList){
            if (task.getTask().toLowerCase().contains(query.toLowerCase())){
                filteredTasks.add(task);
            }
        }
        // Shows the filtered tasks in the recyclerview.
        TasksAdapter adapter = new TasksAdapter(Home.this, filteredTasks);
        recyclerView.setAdapter(adapter);
    }


    // Fetching tasks from the database through Async
    private void getTasks() {
        class GetTasks extends AsyncTask<Void, Void, List<Task>>{
            @Override
            protected List<Task> doInBackground(Void... voids){
                // Fetching tasks via DatabaseClient from the database
                List<Task> taskList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .taskDao()
                        .getAll();
                return taskList;
            }
            // Recyclerview set up with tasks fetched
            @Override
            protected void onPostExecute(List<Task> tasks){
                super.onPostExecute(tasks);
                taskList = tasks;

                TasksAdapter adapter = new TasksAdapter(Home.this, tasks);
                recyclerView.setLayoutManager(new LinearLayoutManager(Home.this));
                recyclerView.setAdapter(adapter);

                // Apply smooth appearance for the items appearing in the recyclerview
                animateRecyclerViewItems();
            }
        }
        GetTasks gt = new GetTasks();
        gt.execute();
    }


    // Appearance settings for the cards in recyclerview
    private void animateRecyclerViewItems(){
        recyclerView.setAlpha(0f);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.animate()
                .alpha(1f)
                .setDuration(600)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }
}