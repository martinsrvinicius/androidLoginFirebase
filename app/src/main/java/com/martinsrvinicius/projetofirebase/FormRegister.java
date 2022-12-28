package com.martinsrvinicius.projetofirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.martinsrvinicius.projetofirebase.databinding.ActivityFormRegisterBinding;

public class FormRegister extends AppCompatActivity {

    private ActivityFormRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
    }
}