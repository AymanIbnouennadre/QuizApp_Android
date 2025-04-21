package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    ImageView imgQuiz, imgLocalisation, imgRobot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imgQuiz = findViewById(R.id.imgQuiz);
        imgLocalisation = findViewById(R.id.imgLocalisation);
        imgRobot = findViewById(R.id.imgRobot);

        // ▶️ Clic sur l'image Quiz
        imgQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Quiz1.class));
            }
        });
    }
}
