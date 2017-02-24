package com.example.juancacosta.simpletodo;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Juan C. Acosta on 1/24/2017.
 *
 */

class TodoItemAdapter extends RecyclerView.Adapter<TodoItemAdapter.ViewHolder> {

    private ArrayList<Todo> todoList = new ArrayList<>();
    private Animation add, remove;

    TodoItemAdapter(ArrayList<Todo> todoList) {
        Collections.sort(todoList, new Comparator<Todo>() {
            @Override
            public int compare(Todo todo, Todo t1) {
                return Integer.valueOf(todo.getPriority()).compareTo(t1.getPriority());
            }
        });
        Collections.sort(todoList, new Comparator<Todo>() {
            @Override
            public int compare(Todo todo, Todo t1) {
                return t1.getDate().compareTo(todo.getDate());
            }
        });
        this.todoList = todoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.element_todo_adapter, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Todo todo = todoList.get(position);
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(com.example.juancacosta.simpletodo.BR.todo, todo);
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

        private ViewDataBinding binding;

        ViewHolder(ViewDataBinding view) {
            super(view.getRoot());
            binding = view;
            binding.executePendingBindings();
        }

        ViewDataBinding getBinding() {
            return binding;
        }
    }
}
