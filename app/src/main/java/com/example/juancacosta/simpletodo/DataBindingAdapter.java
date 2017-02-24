package com.example.juancacosta.simpletodo;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Juan C. Acosta on 2/19/2017.
 */

public class DataBindingAdapter {

    private DataBindingAdapter(){}

    @BindingAdapter("priorityBackground")
    public static void setPriorityBackground(View view, int priority){
        Context context = view.getContext();
        int[] color = context.getResources().getIntArray(R.array.task_color);
        view.setBackgroundColor(color[priority]);
    }

    @BindingAdapter("priorityText")
    public static void setPriorityText(TextView text, int priority){
        Context context = text.getContext();
        String[] priorityText = context.getResources().getStringArray(R.array.task_priority);
        text.setText(priorityText[priority]);
    }
}
