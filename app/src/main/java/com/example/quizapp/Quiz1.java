package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;


import androidx.appcompat.app.AppCompatActivity;



public class Quiz1 extends AppCompatActivity {

    TextView tvQuestion;
    RadioGroup radioGroup;
    RadioButton rb1, rb2;
    ImageView quizImage;
    Button bNext, bExit;
    FirebaseFirestore db;

    String correctAnswer = "";
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz1);

        // 🔹 Liaison des vues
        tvQuestion = findViewById(R.id.tvQ1);
        radioGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        quizImage = findViewById(R.id.quizImage);
        bNext = findViewById(R.id.bNext);
        bExit = findViewById(R.id.bexit);

        score = getIntent().getIntExtra("score", 0);
        db = FirebaseFirestore.getInstance();

        // 🔹 Charger les données du quiz depuis Firestore
        db.collection("quizzes").document("1").get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                QuizModel quiz = documentSnapshot.toObject(QuizModel.class);
                if (quiz != null) {
                    tvQuestion.setText(quiz.getQuestion());
                    String correct = quiz.getCorrectAnswer();
                    String incorrect = quiz.getFalseAnswer();

                    if (Math.random() > 0.5) {
                        rb1.setText(correct);
                        rb2.setText(incorrect);
                    } else {
                        rb1.setText(incorrect);
                        rb2.setText(correct);
                    }

                    correctAnswer = correct;


                    int resId = getResources().getIdentifier(quiz.getImage(), "drawable", getPackageName());
                    quizImage.setImageResource(resId);
                }
            }
        });

        // 🔹 Bouton Next
        bNext.setOnClickListener(v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Veuillez sélectionner une réponse", Toast.LENGTH_SHORT).show();
            } else {
                RadioButton selected = findViewById(selectedId);
                if (selected.getText().toString().equals(correctAnswer)) {
                    score++;
                }
                Intent intent = new Intent(Quiz1.this, Quiz2.class);
                intent.putExtra("score", score);
                startActivity(intent);
                finish();
            }
        });

        // 🔹 Bouton Exit
        bExit.setOnClickListener(v -> {
            startActivity(new Intent(Quiz1.this, Home.class));
            finish();
        });
    }
}
