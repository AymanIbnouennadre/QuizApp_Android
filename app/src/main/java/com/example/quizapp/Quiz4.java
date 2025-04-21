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

public class Quiz4 extends AppCompatActivity {

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
        setContentView(R.layout.activity_quiz4);

        // Liaison UI
        tvQuestion = findViewById(R.id.tvQ1);
        radioGroup = findViewById(R.id.rg);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        quizImage = findViewById(R.id.quizImage);
        bNext = findViewById(R.id.bNext);
        bExit = findViewById(R.id.bexit);

        score = getIntent().getIntExtra("score", 0);
        db = FirebaseFirestore.getInstance();

        // Charger données Firestore
        db.collection("quizzes").document("4").get().addOnSuccessListener(documentSnapshot -> {
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

        bNext.setOnClickListener(v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Merci de choisir une réponse S.V.P !", Toast.LENGTH_SHORT).show();
            } else {
                RadioButton selected = findViewById(selectedId);
                if (selected.getText().toString().equals(correctAnswer)) {
                    score++;
                }
                Intent intent = new Intent(Quiz4.this, Quiz5.class);
                intent.putExtra("score", score);
                startActivity(intent);
                finish();
            }
        });

        bExit.setOnClickListener(v -> {
            startActivity(new Intent(Quiz4.this, Home.class));
            finish();
        });
    }
}
