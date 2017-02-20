package com.example.juancacosta.simpletodo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Juan C. Acosta on 1/24/2017.
 *
 */

class TodoItemAdapter extends RecyclerView.Adapter<TodoItemAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Todo> todoList = new ArrayList<>();
    private LayoutInflater inflater;

    TodoItemAdapter(Context context, ArrayList<Todo> todoList) {
        this.context = context;
        this.todoList = todoList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = inflater.inflate(R.layout.element_todo_adapter, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvItemName.setText(todoList.get(position).getName());
        String[] priority = context.getResources().getStringArray(R.array.task_priority);
        int[] color = context.getResources().getIntArray(R.array.task_color);
        holder.tvItemPriority.setText(priority[todoList.get(position).getPriority()]);
        holder.itemView.setBackgroundColor(color[todoList.get(position).getPriority()]);

    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    void add(Todo todo) {
        todoList.add(todo);
        notifyDataSetChanged();
    }

    void remove(int position) {
        todoList.remove(position);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName,tvItemPriority;
        View itemView;


        ViewHolder(View view) {
            super(view);
            itemView = view;
            tvItemName = (TextView) view.findViewById(R.id.tvItemName);
            tvItemPriority = (TextView) view.findViewById(R.id.tvItemPriority);
        }
    }
}
