package com.course.tvapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ExampleViewHolder> {
    private ArrayList<TaskAdapter> mExampleList;
    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.txt_task_name);
            mTextView1 = itemView.findViewById(R.id.txt_date);
            mTextView2 = itemView.findViewById(R.id.txt_date2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
    public void ExampleAdapter(ArrayList<TaskAdapter> exampleList) {
        mExampleList = exampleList;
    }
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.programs, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }


    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        TaskAdapter currentItem = mExampleList.get(position);
        Log.d("Daily", "taskModelList: " + mExampleList);
        holder.mImageView.setText(String.valueOf(currentItem.getTaskName()));
        holder.mTextView1.setText(String.valueOf(taskModelList.get(position).getTaskAddedTime()));
        holder.mTextView2.setText(currentItem.getText2());
    }
    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}*/
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.Holder> {
    private Context context;
    private List<TaskModel> taskModelList;
    private onItemClick mListener;

    public interface onItemClick {
        void itemClick(View v, int position);
    }

    public void setOnItemClick(onItemClick listener){
        mListener = listener;
    }

    public TaskAdapter(Context context, List<TaskModel> taskModelList, onItemClick mListener) {
        this.context = context;
        this.taskModelList = taskModelList;
        this.mListener = mListener;
        Collections.reverse(taskModelList);
    }

    public void setTaskModelList(List<TaskModel> taskModelList) {
        this.taskModelList = taskModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context)
                .inflate(R.layout.programs, parent, false), mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Log.d("Daily", "taskModelList: " + taskModelList);
        holder.txtTaskName.setText(taskModelList.get(position).getTaskName());
        holder.txtTaskAddTime.setText(String.valueOf(taskModelList.get(position).getTaskAddedTime()));
        holder.txtTaskAddTime1.setText(String.valueOf(taskModelList.get(position).getDescription()));
    }

    @Override
    public int getItemCount() {
        return taskModelList != null ? taskModelList.size() : 0;
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtTaskName, txtTaskAddTime, txtTaskAddTime1;
        public Holder(@NonNull View itemView, final onItemClick listener) {
            super(itemView);
            txtTaskName = itemView.findViewById(R.id.txt_task_name);
            txtTaskAddTime = itemView.findViewById(R.id.txt_date);
            txtTaskAddTime1 = itemView.findViewById(R.id.txt_date2);
            itemView.setOnClickListener(this);
            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION) {
                        listener.itemClick(view, position);
                    }
                    }
                }
            });*/
        }

        @Override
        public void onClick(View view) {
            mListener.itemClick(view, getAdapterPosition());
        }
    }

}