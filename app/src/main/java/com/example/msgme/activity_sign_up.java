package com.example.msgme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.msgme.Models.Users;
import com.example.msgme.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class activity_sign_up extends AppCompatActivity {
    ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        getSupportActionBar().hide();

        progressDialog = new ProgressDialog(activity_sign_up.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We're creating your your account");


        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.username.getText().toString() .isEmpty()){
                    binding.username.setError("Enter your Name");
                    Toast.makeText(activity_sign_up.this,"enter name", Toast.LENGTH_SHORT).show();

                }
                if (binding.etEmail.getText().toString() .isEmpty()){
                    binding.etEmail.setError("Enter your Email");
                    Toast.makeText(activity_sign_up.this,"enter email", Toast.LENGTH_SHORT).show();
                }
                if (binding.etPassword.getText().toString() .isEmpty()){
                    binding.etPassword.setError("Enter your Password");
                    Toast.makeText(activity_sign_up.this,"enter pass", Toast.LENGTH_SHORT).show();

                }
                progressDialog.show();

                mAuth.createUserWithEmailAndPassword(binding.etEmail.getText().toString(),binding.etPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            Users user=new Users(binding.username.getText().toString(),binding.etEmail.getText().toString(),binding.etPassword.getText().toString());
                            String id=task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(user);
                            Toast.makeText(activity_sign_up.this,"done", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(activity_sign_up.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        binding.alreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_sign_up.this,activity_sign_in.class);
                startActivity(intent);
            }
        });
    }
}