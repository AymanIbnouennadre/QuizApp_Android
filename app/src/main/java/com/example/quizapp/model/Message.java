package com.example.quizapp.model;

public class Message {
    private String content;
    private String sender; // "user" ou "bot"

    public Message(String content, String sender) {
        this.content = content;
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }
}
