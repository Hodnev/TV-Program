package com.course.tvapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<TaskModel> taskList;
    private TaskAdapter adapter;
    private TaskAdapter.onItemClick listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);


        recyclerView = findViewById(R.id.recyclerview1);
        taskList = PrefConfig.readListFromPref(this);

        if (taskList == null)
            taskList = new ArrayList<>();
        setOnClickListener();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new TaskAdapter(getApplicationContext(), taskList, listener);
        Collections.reverse(taskList);
        recyclerView.setAdapter(adapter);
    }
    private void setOnClickListener() {
        listener = new TaskAdapter.onItemClick() {
            @Override
            public void itemClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), ProgramActivity.class);
                intent.putExtra("channel", taskList.get(position).getTaskName());
                intent.putExtra( "time", taskList.get(position).getTaskAddedTime());
                intent.putExtra("description", taskList.get(position).getDescription());
                startActivity(intent);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_panel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_by_channel:
                Collections.sort(taskList, new Comparator<TaskModel>() {
                    @Override
                    public int compare(TaskModel taskModel, TaskModel t1) {
                        return taskModel.getTaskName().compareTo(t1.getTaskName());
                    }
                });
                //PrefConfig.writeListInPref(getApplicationContext(), taskList);
                //Collections.reverse(taskList);
                adapter.setTaskModelList(taskList);
                Toast.makeText(this, "Data is sorted by channel", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.sort_by_time:
                Collections.sort(taskList, new Comparator<TaskModel>() {
                    @Override
                    public int compare(TaskModel taskModel, TaskModel t2) {
                        return taskModel.getTaskAddedTime().compareTo(t2.getTaskAddedTime());
                    }
                });
                //PrefConfig.writeListInPref(getApplicationContext(), taskList);
                Collections.reverse(taskList);
                adapter.setTaskModelList(taskList);
                Toast.makeText(this, "Data is sorted by time", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
