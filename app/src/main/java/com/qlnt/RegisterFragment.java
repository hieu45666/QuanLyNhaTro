package com.qlnt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qlnt.Data.DatabaseHelper;

public class RegisterFragment extends Fragment {

    private Button buttonRegister;
    private EditText editTextUsername, editTextPassword, editTextConfirmPassword;
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
                if (db.addUser(username, password) == -1) {
                    Toast.makeText(getContext(), "Username đã tồn tại", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    // Chuyển sang LoginFragment sau khi đăng ký thành công
                    ViewPager2 viewPager = getActivity().findViewById(R.id.viewPager);
                    viewPager.setCurrentItem(0);
                }
            }
        });

        return view;
    }
}