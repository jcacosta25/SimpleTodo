package com.example.juancacosta.simpletodo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by Juan C. Acosta on 1/25/2017.
 * AlertDialogFragment
 */

public class NewTaskDialogFragment extends DialogFragment {

    EditText taskName, taskDate, taskNotes;
    Spinner taskPriority, taskStatus;

    public NewTaskDialogFragment() {
    }

    public static NewTaskDialogFragment newInstance(String title) {
        NewTaskDialogFragment fragment = new NewTaskDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);

        View view = View.inflate(getContext(), R.layout.fragment_todo_add, null);
        taskName = (EditText) view.findViewById(R.id.et_todo_name);
        taskDate = (EditText) view.findViewById(R.id.et_todo_date);
        taskNotes = (EditText) view.findViewById(R.id.et_todo_notes);
        taskPriority = (Spinner) view.findViewById(R.id.spinner_priority);
        taskStatus = (Spinner) view.findViewById(R.id.spinner_status);

        alertDialogBuilder.setView(view);

        alertDialogBuilder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // on success
                Todo todo = new Todo();
                todo.setName(taskName.getText().toString());
                todo.setDate(taskDate.getText().toString());
                todo.setNotes(taskNotes.getText().toString());
                todo.setPriority(taskPriority.getSelectedItemPosition());
                todo.setStatus(taskStatus.getSelectedItemPosition());
                NewTaskDialogListener listener = (NewTaskDialogListener) getActivity();
                listener.onFinishNewTaskDialog(todo);

            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        return alertDialogBuilder.create();
    }

    public interface NewTaskDialogListener {
        void onFinishNewTaskDialog(Todo todo);
    }


}
