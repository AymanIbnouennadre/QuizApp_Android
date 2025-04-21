package com.example.quizapp;

public class QuizModel {
    private String question;
    private String correctAnswer;
    private String falseAnswer;
    private String image;

    public QuizModel() {} // Obligatoire pour Firebase

    public QuizModel(String question, String correctAnswer, String falseAnswer, String image) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.falseAnswer = falseAnswer;
        this.image = image;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getFalseAnswer() {
        return falseAnswer;
    }

    public String getImage() {
        return image;
    }
}
