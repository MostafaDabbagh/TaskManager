package com.example.taskmanager.controller.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanager.R;
import com.example.taskmanager.Repository.UserRepository;
import com.example.taskmanager.controller.activity.TaskPagerActivity;
import com.example.taskmanager.model.User;

public class LoginFragment extends Fragment {

    public static final int REQUEST_CODE_SIGN_UP_DIALOG = 0;
    private UserRepository mUserRepository;

    private EditText mEditTextUsername;
    private EditText mEditTextPassword;
    private Button mButtonLogin;
    private Button mButtonSignUp;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserRepository = UserRepository.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        findViews(view);
        setListeners();
        return view;
    }

    private void findViews(View view) {
        mEditTextUsername = view.findViewById(R.id.edit_text_username);
        mEditTextPassword = view.findViewById(R.id.edit_text_password);
        mButtonLogin = view.findViewById(R.id.button_login);
        mButtonSignUp = view.findViewById(R.id.button_signup);
    }

    private void setListeners() {
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUserRepository.userExists(mEditTextUsername.getText().toString())) {
                    User user = mUserRepository.get(mEditTextUsername.getText().toString());
                    String password = user.getPassword();
                    String enteredPassword = mEditTextPassword.getText().toString();
                    if (password.equals(enteredPassword)) {
                        // Start TaskPagerActivity
                        User currentUser = mUserRepository.get(mEditTextUsername.getText().toString());
                        UserRepository.getInstance(getActivity()).setCurrentUser(currentUser);
                        Intent intent = TaskPagerActivity.newIntent(getActivity());
                        startActivity(intent);
                    } else
                        Toast.makeText(getActivity(), "Password is incorrect!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "User is not found. Please sign up first.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignUpDialog();
            }
        });

    }

    private void startSignUpDialog() {
        SignUpDialogFragment signUpDialogFragment = SignUpDialogFragment.newInstance();
        signUpDialogFragment.setTargetFragment(LoginFragment.this, REQUEST_CODE_SIGN_UP_DIALOG);
        signUpDialogFragment.show(getFragmentManager(), "signUpDialog");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        User user = (User) data.getSerializableExtra(SignUpDialogFragment.EXTRA_CREATED_USER);
        UserRepository.getInstance(getActivity()).add(user);
        mEditTextUsername.setText(user.getUsername(), TextView.BufferType.EDITABLE);
        mEditTextPassword.setText(user.getPassword(), TextView.BufferType.EDITABLE);
    }
}