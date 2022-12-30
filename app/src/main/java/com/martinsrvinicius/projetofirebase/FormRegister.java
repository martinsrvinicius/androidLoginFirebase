package com.martinsrvinicius.projetofirebase;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.martinsrvinicius.projetofirebase.databinding.ActivityFormRegisterBinding;

import java.util.HashMap;
import java.util.Map;

public class FormRegister extends AppCompatActivity {

    private ActivityFormRegisterBinding binding;
    private String[] messages = {"Preencha todos os campos", "Cadastro realizado com sucesso!"};
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = binding.editNome.getText().toString();
                String email = binding.editEmail.getText().toString();
                String password = binding.editPassword.getText().toString();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, messages[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                } else {
                    createNewUser(view, name, email, password);
                }
            }
        });
    }

    private void createNewUser(View view, String name, String email, String password) {
        Log.d(TAG, "createNewUser: " + email + " " + password);

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            saveUserData(name);
                            Snackbar snackbar = Snackbar.make(view, messages[1], Snackbar.LENGTH_SHORT);
                            snackbar.setBackgroundTint(Color.WHITE);
                            snackbar.setTextColor(Color.BLACK);
                            snackbar.show();
                        } else {
                            String erro;
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                erro = "Digite uma senha com no mínimo 6 caracteres";
                            } catch (FirebaseAuthUserCollisionException e) {
                                erro = "Esta conta já foi cadastrada";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                erro = "E-mail inválido";
                            } catch (Exception e) {
                                erro = "Erro ao cadastrar usuário";
                            }

                            Snackbar snackbar = Snackbar.make(view, erro, Snackbar.LENGTH_SHORT);
                            snackbar.setBackgroundTint(Color.WHITE);
                            snackbar.setTextColor(Color.BLACK);
                            snackbar.show();
                        }
                    }
                });
    }

    public void saveUserData(String name) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> users = new HashMap<>();
        users.put("nome", name);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuários").document(userId);
        documentReference.set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "onSuccess: Sucesso ao salvar os dados");
                profileIntent();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: Erro ao salvar os dados " + e.toString());

            }
        });
    }

    private void profileIntent() {
        Intent intent = new Intent(FormRegister.this, Profile.class);
        startActivity(intent);
        finish();
    }
}