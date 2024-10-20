package com.qlnt;

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

public class RegisterFragment extends Fragment {

    private EditText editTextUsername, editTextPassword, editTextConfirmPassword;
    private Button buttonRegister;
    private DatabaseHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        editTextUsername = view.findViewById(R.id.editTextRegUsername);
        editTextPassword = view.findViewById(R.id.editTextRegPassword);
        editTextConfirmPassword = view.findViewById(R.id.editTextRegConfirmPassword);
        buttonRegister = view.findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();
            String confirmPassword = editTextConfirmPassword.getText().toString();
            db = new DatabaseHelper(getContext());

            if (!password.equals(confirmPassword)) {
                Toast.makeText(getContext(), "Password không trùng khớp!", Toast.LENGTH_SHORT).show();
            } else {
                if (db.addUser(username,password) == -1) {
                    Toast.makeText(getContext(),"Username đã tồn tại", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getContext(),"Đăng ký thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
