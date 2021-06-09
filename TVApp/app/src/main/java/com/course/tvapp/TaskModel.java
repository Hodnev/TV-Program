package com.course.tvapp;

import java.io.Serializable;

public class TaskModel implements Serializable{
    private String channel_name;
    private String  time;
    private String  description;



    public TaskModel(String channelName, String  taskAddedTime, String taskLine) {
        this.channel_name = channelName;
        this.time = taskAddedTime;
        this.description = taskLine;

    }


    public String getTaskName() {
        return channel_name;
    }

    public void setChannelName(String text) {
        this.channel_name = text;
    }

    public String getTaskAddedTime() {
        return time;
    }
    public void setTime(String text) {
        this.time = text;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String text) {this.description = text;
    }

}
