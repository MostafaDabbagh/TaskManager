package com.example.taskmanager.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskmanager.R;
import com.example.taskmanager.Repository.UserRepository;
import com.example.taskmanager.model.User;

public class SignUpDialogFragment extends DialogFragment {

    public static final String EXTRA_CREATED_USER = "com.example.taskmanager.controller.fragment.createdUser";
    private EditText mEditTextUsername;
    private EditText mEditTextPassword;

    public static SignUpDialogFragment newInstance() {
        SignUpDialogFragment fragment = new SignUpDialogFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_fragment_sign_up, null);
        findViews(view);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("Sign up", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent();
                        String usernameStr = mEditTextUsername.getText().toString();
                        String passwordStr = mEditTextPassword.getText().toString();
                        User user = new User(usernameStr, passwordStr);
                        if (UserRepository.getInstance(getActivity()).userExists(usernameStr))
                            Toast.makeText(getActivity(), "Username is already taken", Toast.LENGTH_SHORT).show();
                        else {
                            intent.putExtra(EXTRA_CREATED_USER, user);
                            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                        }
                    }
                })
                .create();
    }

    private void findViews(View view) {
        mEditTextUsername = view.findViewById(R.id.dialog_edit_text_username);
        mEditTextPassword = view.findViewById(R.id.dialog_edit_text_password);
    }
}