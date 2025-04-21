package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Register extends AppCompatActivity {
    //Step 1 Déclaration
    EditText etUsername , etEmail , etPassword , etConfirmPassword;
    Button bRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        // Step 2 Récupération de ids
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        bRegister = findViewById(R.id.bRegister);
        //Step 3 Association de Listeners
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Step 4 Traitement
                if((etUsername.getText().toString().equals("ayman") && etEmail.getText().toString().equals("ayman.ibnouennadre@gmail.com") ) && etPassword.getText().toString().equals("123") && etConfirmPassword.getText().toString().equals("123")){
                    startActivity(new Intent(Register.this, Quiz1.class));
                }
                else {
                    Toast.makeText(getApplicationContext(), "Try Again ", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}