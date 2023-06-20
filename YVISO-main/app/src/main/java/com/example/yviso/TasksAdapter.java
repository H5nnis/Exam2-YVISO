package com.example.yviso;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import android.widget.CheckBox;


public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder>{

    private Context mCtx;
    private List<Task> taskList;

    public TasksAdapter(Context mCtx, List<Task> taskList){
        this.mCtx = mCtx;
        this.taskList = taskList;
    }

    // Resizing the layout, view the recyclerview_tasks xml layout.
    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_tasks, parent, false);
        return new TasksViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position){
        // Get task from position.
        Task t = taskList.get(position);

        // Setting the task details.
        holder.textViewTask.setText(t.getTask());
        holder.textViewDesc.setText(t.getDesc());
        holder.details.setText(t.getDetails());
        holder.checkViewMot.setChecked(t.isCheckMot());
        holder.checkViewService.setChecked(t.isCheckService());
        holder.textViewDate.setText(t.getDate());
    }


    // Returns the total item amount from list.
    @Override
    public int getItemCount() {return taskList.size();
    }


    class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textViewTask, textViewDesc, details, textViewDate;
        LinearLayout visibleLayout, invisibleLayout;
        FloatingActionButton editInfoFab;
        public CheckBox checkViewMot;
        public CheckBox checkViewService;

        public TasksViewHolder(View itemView){
            super(itemView);

            // Text view.
            textViewTask = itemView.findViewById(R.id.textViewTask);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            details = itemView.findViewById(R.id.details);

            // Checkboxes
            checkViewService = itemView.findViewById(R.id.checkBoxService);
            checkViewMot = itemView.findViewById(R.id.checkBoxMot);

            // Hidden/Shown layout.
            visibleLayout = itemView.findViewById(R.id.visibleLayout);
            invisibleLayout = itemView.findViewById(R.id.invisibleLayout);
            editInfoFab = itemView.findViewById(R.id.editInfoFab);
            invisibleLayout.setVisibility(View.GONE);

            // Onclick listener for layout and fab.
            visibleLayout.setOnClickListener(this);
            editInfoFab.setOnClickListener(this);
        }


        @Override
        public void onClick(View view){
            if (view.getId() == R.id.visibleLayout){
                toggleInvisibleLayout();
            }else if (view.getId() == R.id.editInfoFab){
                // Get selected task.
                Task task = taskList.get(getAdapterPosition());
                // Start update of selected item.
                Intent intent = new Intent(mCtx, UpdateTaskActivity.class);
                intent.putExtra("task", task);
                mCtx.startActivity(intent);
            }
        }


        // On/off for invisible layout.
        private void toggleInvisibleLayout(){
            if (invisibleLayout.getVisibility() == View.VISIBLE){
                invisibleLayout.setVisibility(View.GONE);
            }else{
                invisibleLayout.setVisibility(View.VISIBLE);
            }
        }
    }
}