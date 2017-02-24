package com.example.juancacosta.simpletodo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Juan C. Acosta on 1/25/2017.
 * AlertDialogFragment
 */

public class NewTaskDialogFragment extends DialogFragment {

    private EditText taskName, taskNotes, taskDate;
    private Spinner taskPriority, taskStatus;
    private String[] action = {"Create", "Edit"};
    private int year, month, day;


    public NewTaskDialogFragment() {
    }

    public static NewTaskDialogFragment newInstance(String title) {
        NewTaskDialogFragment fragment = new NewTaskDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("position", 0);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        final Todo item = getArguments().getParcelable("Edit");
        final int position = getArguments().getInt("position");

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        View view = View.inflate(getContext(), R.layout.fragment_todo_add, null);
        taskName = (EditText) view.findViewById(R.id.et_todo_name);
        taskDate = (EditText) view.findViewById(R.id.et_todo_date);
        taskNotes = (EditText) view.findViewById(R.id.et_todo_notes);
        taskPriority = (Spinner) view.findViewById(R.id.spinner_priority);
        taskStatus = (Spinner) view.findViewById(R.id.spinner_status);

        alertDialogBuilder.setView(view);

        taskDate.setInputType(InputType.TYPE_NULL);
        taskDate.requestFocus();
        taskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateDialog();
            }
        });

        if (item != null) {
            taskName.setText(item.getName());
            taskDate.setText(item.getDate());
            taskNotes.setText(item.getNotes());
            taskPriority.setSelection(item.getPriority());
            taskStatus.setSelection(item.getStatus());
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
            try {
                Date date = formatter.parse(item.getDate());
                year = date.getYear();
                month = date.getMonth();
                day = date.getDay();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            alertDialogBuilder.setPositiveButton(action[1], new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    item.setName(taskName.getText().toString());
                    item.setDate(taskDate.getText().toString());
                    item.setNotes(taskNotes.getText().toString());
                    item.setPriority(taskPriority.getSelectedItemPosition());
                    item.setStatus(taskStatus.getSelectedItemPosition());
                    NewTaskDialogListener listener = (NewTaskDialogListener) getActivity();
                    listener.onFinishEditTaskDialog(item, position);


                }
            });

        } else {
            alertDialogBuilder.setPositiveButton(action[0], new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // on success
                    Todo todo = new Todo(taskName.getText().toString(),
                            taskDate.getText().toString(),
                            taskNotes.getText().toString(),
                            taskPriority.getSelectedItemPosition(),
                            taskStatus.getSelectedItemPosition());

                    NewTaskDialogListener listener = (NewTaskDialogListener) getActivity();
                    listener.onFinishNewTaskDialog(todo);

                }
            });

        }

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        return alertDialogBuilder.create();
    }

    public void DateDialog() {

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear = monthOfYear + 1;
                if (monthOfYear >= 10) {
                    taskDate.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                } else {
                    taskDate.setText(dayOfMonth + "/0" + monthOfYear + "/" + year);
                }
            }
        };

        DatePickerDialog dpDialog = new DatePickerDialog(getContext(), listener, year, month, day);

        dpDialog.show();

    }

    public interface NewTaskDialogListener {
        void onFinishNewTaskDialog(Todo todo);

        void onFinishEditTaskDialog(Todo todo, int position);
    }


}
