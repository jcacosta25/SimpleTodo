package com.example.juancacosta.simpletodo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NewTaskDialogFragment.NewTaskDialogListener {
    private ArrayList<Todo> todoList = new ArrayList<>();
    private TodoItemAdapter itemsAdapter;
    private RecyclerView lvItems;
    private TodoDataBaseHelper db;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new TodoDataBaseHelper(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setupWindowAnimations();
        }
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        int theme = pref.getInt("Theme", R.style.AppTheme);
        setTheme(theme);
        setContentView(R.layout.activity_main);
        readItems();
        lvItems = (RecyclerView) findViewById(R.id.lvItems);
        itemsAdapter = new TodoItemAdapter(todoList);
        itemsAdapter.notifyDataSetChanged();
        lvItems.setAdapter(itemsAdapter);
        lvItems.setLayoutManager(new LinearLayoutManager(this));
        setupListListener();
    }

    private void setupListListener() {
        lvItems.addOnItemTouchListener(new RecyclerTouchListener(this, lvItems, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                FragmentManager fm = getSupportFragmentManager();
                NewTaskDialogFragment newTask = new NewTaskDialogFragment();
                Bundle bundle = new Bundle();
                Todo todo = todoList.get(position);
                bundle.putParcelable("Edit", todo);
                bundle.putString("title", "Edit: " + todoList.get(position).getName());
                bundle.putInt("position", position);
                newTask.setArguments(bundle);
                newTask.show(fm, "fragment_todo_add");
            }

            @Override
            public void onLongClick(View view, final int position) {
                deleteItem(position);
            }
        }));
    }

    public void addOnItem(View v) {
        FragmentManager fm = getSupportFragmentManager();
        NewTaskDialogFragment newTask = NewTaskDialogFragment.newInstance("New Task");
        newTask.show(fm, "fragment_todo_add");
    }

    public void deleteItem(final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle("\"Are you sure you want to delete this task  ?\"");
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteTodo(todoList.get(position));
                itemsAdapter.remove(position);
                readItems();
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

    private void readItems() {
        todoList = db.getAllTodo();
    }

    @Override
    public void onFinishNewTaskDialog(Todo todo) {
        itemsAdapter.add(todo);
        db.addTodo(todo);
        readItems();
    }

    @Override
    public void onFinishEditTaskDialog(Todo todo, int position) {
        db.updateTodo(todo);
        itemsAdapter.notifyDataSetChanged();
        itemsAdapter.notifyItemChanged(position);
        readItems();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setupWindowAnimations() {
        Slide slide = (Slide) TransitionInflater.from(this).inflateTransition(R.transition.activity_slide);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setExitTransition(slide);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        SharedPreferences.Editor editor = pref.edit();
        switch (item.getItemId()) {
            case R.id.clasic:
                editor.putInt("Theme", R.style.AppTheme);
                editor.apply();
                MainActivity.this.recreate();
                return true;
            case R.id.red:
                editor.putInt("Theme", R.style.AppTheme_Red);
                editor.apply();
                MainActivity.this.recreate();
                return true;
            case R.id.dark:
                editor.putInt("Theme", R.style.AppTheme_Dark);
                editor.apply();
                MainActivity.this.recreate();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

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
