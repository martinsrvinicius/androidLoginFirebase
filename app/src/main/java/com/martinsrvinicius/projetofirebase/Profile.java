package com.martinsrvinicius.projetofirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.martinsrvinicius.projetofirebase.databinding.ActivityProfileBinding;

public class Profile extends AppCompatActivity {

    private ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();



    }
}