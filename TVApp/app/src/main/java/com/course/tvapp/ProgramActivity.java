package com.course.tvapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProgramActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tv_program);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView channelTxt = findViewById(R.id.channel);
        TextView timeTxt = findViewById(R.id.time);
        TextView descriptionTxt = findViewById(R.id.desc);

        String channel = "Channel name not set";
        String time = "Time not set";
        String discription = "Description not set";

        Bundle extras = getIntent().getExtras();
        if (extras!= null){
            channel = extras.getString("channel");
            time = extras.getString("time");
            discription = extras.getString("description");
        }
        channelTxt.setText(channel);
        timeTxt.setText(time);
        descriptionTxt.setText(discription);
    }
}
