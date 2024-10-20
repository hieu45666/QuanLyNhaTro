package com.qlnt;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qlnt.Data.DatabaseHelper;

public class LoginFragment extends Fragment {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private DatabaseHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        buttonLogin = view.findViewById(R.id.buttonLogin);
        db = new DatabaseHelper(getContext());

        buttonLogin.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();
            if (db.loginUser(username,password)) {
                Intent intent = new Intent(requireActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            } else{
                Toast.makeText(getContext(), "Thông tin đăng nhập không đúng", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
