package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    // step 1 : Déclaration
    EditText etEmail , etPassword;
    Button bLogin;
    TextView tvRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main); //permet d'afficher
    // Step 2 : Récupération des ids
     etEmail = findViewById(R.id.etEmail);
     etPassword = findViewById(R.id.etPassword);
     bLogin = findViewById(R.id.bLogin);
     tvRegister = findViewById(R.id.tvRegister);
     //Step 3 : Association de Listners

      bLogin.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              // Step 4 Traitement
              if((etEmail.getText().toString().equals("ayman") || etEmail.getText().toString().equals("ayman.ibnouennadre@gmail.com") ) && etPassword.getText().toString().equals("123")){
                  startActivity(new Intent(MainActivity.this, Quiz1.class));
              }
              else {
                  Toast.makeText(getApplicationContext(), "email or password incorrect", Toast.LENGTH_SHORT).show();
              }

          }
      });

      tvRegister.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              // step 4 Traitement
              startActivity(new Intent(getApplicationContext(), Register.class));
          }
      });


    }
}