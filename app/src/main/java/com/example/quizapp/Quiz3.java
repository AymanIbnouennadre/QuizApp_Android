package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class Quiz3 extends AppCompatActivity {

    TextView tvQuestion;
    RadioGroup rg;
    RadioButton rb1, rb2;
    ImageView quizImage;
    Button bNext, bExit;
    FirebaseFirestore db;

    String correctAnswer = "";
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz3);

        // Liaison des vues
        tvQuestion = findViewById(R.id.tvQ1);
        rg = findViewById(R.id.rg);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        quizImage = findViewById(R.id.quizImage);
        bNext = findViewById(R.id.bNext);
        bExit = findViewById(R.id.bexit);

        score = getIntent().getIntExtra("score", 0);
        db = FirebaseFirestore.getInstance();

        // Récupération depuis Firestore
        db.collection("quizzes").document("3").get().addOnSuccessListener(doc -> {
            if (doc.exists()) {
                QuizModel quiz = doc.toObject(QuizModel.class);
                if (quiz != null) {
                    tvQuestion.setText(quiz.getQuestion());
                    String correct = quiz.getCorrectAnswer();
                    String incorrect = quiz.getFalseAnswer();
                    correctAnswer = correct;

                    if (Math.random() > 0.5) {
                        rb1.setText(correct);
                        rb2.setText(incorrect);
                    } else {
                        rb1.setText(incorrect);
                        rb2.setText(correct);
                    }

                    int resId = getResources().getIdentifier(quiz.getImage(), "drawable", getPackageName());
                    quizImage.setImageResource(resId);
                }
            }
        });

        // Next
        bNext.setOnClickListener(v -> {
            int selectedId = rg.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Veuillez sélectionner une réponse", Toast.LENGTH_SHORT).show();
            } else {
                RadioButton selected = findViewById(selectedId);
                if (selected.getText().toString().equals(correctAnswer)) {
                    score++;
                }
                Intent intent = new Intent(Quiz3.this, Quiz4.class);
                intent.putExtra("score", score);
                startActivity(intent);
                finish();
            }
        });

        // Exit
        bExit.setOnClickListener(v -> {
            startActivity(new Intent(Quiz3.this, Home.class));
            finish();
        });
    }
}
