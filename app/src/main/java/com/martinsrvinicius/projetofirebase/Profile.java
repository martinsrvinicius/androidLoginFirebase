package com.martinsrvinicius.projetofirebase;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.martinsrvinicius.projetofirebase.databinding.ActivityProfileBinding;

public class Profile extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String userId;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        binding.logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Profile.this, FormLogin.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        DocumentReference documentReference = db.collection("Usuários").document(userId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    binding.txtUserName.setText(value.getString("nome"));
                    binding.txtUserEmail.setText(email);
                }
            }
        });
    }
}