package com.course.tvapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;



import java.util.ArrayList;

import java.util.Collections;


public class AdminActivity extends AppCompatActivity implements AddDialog.AddDialogListener, DelDialog.DelDialogListener, ChaDialog.ChaDialogListener{


    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private TaskAdapter.onItemClick listener;
    private ArrayList<TaskModel> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_panel);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton btn_add = findViewById(R.id.add_task);
        FloatingActionButton btn_del = findViewById(R.id.remove_task);
        FloatingActionButton btn_cha = findViewById(R.id.change_task);

        recyclerView = findViewById(R.id.recyclerview);

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




        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAdd();
            }
        });

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogDel();
            }
        });

        btn_cha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogCha();
            }
        });


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



    public void openDialogAdd() {
        AddDialog exampleDialog = new AddDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }
    @Override
    public void applyTextsAdd(String channel, String program, String time) {

        TaskModel taskModel = new TaskModel(channel, program, time);
        taskList.add(0, taskModel);
        adapter.notifyItemInserted(0);

        PrefConfig.writeListInPref(getApplicationContext(), taskList);

    }



    public void openDialogDel() {
        DelDialog exampleDialog = new DelDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }
    @Override
    public void applyTextsDel(int position) {
        if (position-1>taskList.size()||position-1<0){
            Toast.makeText(AdminActivity.this, "Position out of range", Toast.LENGTH_SHORT).show();

        }
        else {
            taskList.remove(position - 1);

            adapter.notifyItemRemoved(position - 1);
            PrefConfig.writeListInPref(getApplicationContext(), taskList);
        }
    }



    public void openDialogCha() {
        ChaDialog exampleDialog = new ChaDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }
    @Override
    public void applyTextsCha(int position, String channel, String time, String program) {

        if (position - 1 > taskList.size() || position - 1 < 0) {
            Toast.makeText(AdminActivity.this, "Position out of range", Toast.LENGTH_SHORT).show();

        } else {
            taskList.get(position - 1).setChannelName(channel);
            taskList.get(position - 1).setTime(time);
            taskList.get(position - 1).setDescription(program);

            adapter.notifyItemChanged(position - 1);
            PrefConfig.writeListInPref(getApplicationContext(), taskList);
        }
    }

}
