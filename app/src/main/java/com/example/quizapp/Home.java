package com.example.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    ImageView imgQuiz, imgLocalisation, imgRobot;
    Button btnBack;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imgQuiz = findViewById(R.id.imgQuiz);
        imgLocalisation = findViewById(R.id.imgLocalisation);
        imgRobot = findViewById(R.id.imgRobot);
        btnBack = findViewById(R.id.btnBack);

        // ▶️ Clic sur l'image Quiz
        imgQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Quiz1.class));
            }
        });

        // ▶️ Clic sur l'image de localisation
        imgLocalisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, MapActivity.class));
            }
        });
        // ▶️ Clic sur l'image de chatbot
        imgRobot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, ChatbotActivity.class));
            }
        });
        // ▶️ Clic sur button de sortir
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, MainActivity.class));
            }
        });


    }
}
