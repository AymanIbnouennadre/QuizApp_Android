package com.example.quizapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.model.Message;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatbotActivity extends AppCompatActivity {

    private RecyclerView chatRecyclerView;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;
    private EditText editTextMessage;
    private Button buttonSend;

    // Utilise l'adresse 10.0.2.2 pour accéder à l'API local depuis l'émulateur Android
    private final String API_URL = "http://10.0.2.2:8000/assitante_vocal/";
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList, this);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(messageAdapter);

        buttonSend.setOnClickListener(v -> {
            String userMessage = editTextMessage.getText().toString().trim();
            if (!userMessage.isEmpty()) {
                addMessage(userMessage, "user");
                editTextMessage.setText("");
                callChatAPI(userMessage);
            }
        });
    }

    private void addMessage(String content, String sender) {
        messageList.add(new Message(content, sender));
        messageAdapter.notifyItemInserted(messageList.size() - 1);
        chatRecyclerView.scrollToPosition(messageList.size() - 1);
    }

    private void callChatAPI(String text) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("text", text);
            jsonBody.put("session_id", "default");
        } catch (Exception e) {
            runOnUiThread(() -> addMessage("Erreur JSON", "bot"));
            return;
        }

        RequestBody body = RequestBody.create(
                jsonBody.toString(),
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> addMessage("Erreur : " + e.getMessage(), "bot"));
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String responseStr = response.body().string();
                        JSONObject json = new JSONObject(responseStr);
                        String botReply = json.getString("response");

                        runOnUiThread(() -> addMessage(botReply, "bot"));
                    } catch (Exception e) {
                        runOnUiThread(() -> addMessage("Erreur parsing JSON", "bot"));
                    }
                } else {
                    runOnUiThread(() -> addMessage("Erreur serveur", "bot"));
                }
            }
        });
    }
}
