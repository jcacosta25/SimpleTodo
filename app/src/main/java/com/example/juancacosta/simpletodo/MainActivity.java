package com.example.juancacosta.simpletodo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NewTaskDialogFragment.NewTaskDialogListener {
    ArrayList<Todo> todoList = new ArrayList<>();
    TodoItemAdapter itemsAdapter;
    RecyclerView lvItems;
    TodoDataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new TodoDataBaseHelper(this);
        setContentView(R.layout.activity_main);
        readItems();
        lvItems = (RecyclerView) findViewById(R.id.lvItems);
        itemsAdapter = new TodoItemAdapter(getApplicationContext(), todoList);
        lvItems.setAdapter(itemsAdapter);
        lvItems.setLayoutManager(new LinearLayoutManager(this));
        setupListListener();
    }

    private void setupListListener() {
        lvItems.addOnItemTouchListener(new RecyclerTouchListener(this, lvItems, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, final int position) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("\"Are you sure you want to delete this task  ?\"");
                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        itemsAdapter.remove(position);
                        db.deleteTodo(todoList.get(position - 1));
                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }));
    }

    public void addOnItem(View v) {
        FragmentManager fm = getSupportFragmentManager();
        NewTaskDialogFragment newTask = NewTaskDialogFragment.newInstance("New Task");
        newTask.show(fm, "fragment_todo_add");
    }

    private void readItems() {
        todoList = db.getAllTodo();
    }

    @Override
    public void onFinishNewTaskDialog(Todo todo) {
        itemsAdapter.add(todo);
        db.addTodo(todo);
    }

    interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener) {

            this.clicklistener = clicklistener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recycleView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clicklistener != null) {
                        clicklistener.onLongClick(child, recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
                clicklistener.onClick(child, rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
