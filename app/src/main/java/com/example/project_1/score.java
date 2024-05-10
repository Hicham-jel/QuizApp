package com.example.project_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class score extends AppCompatActivity {
    TextView result;
    Button logout;
    Button leaderboard;
    Button trya;

    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    int sc;
    CircularProgressBar circularProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        result = findViewById(R.id.result);
        logout = findViewById(R.id.logout);
        leaderboard=findViewById(R.id.leaderboard);
        trya = findViewById(R.id.buttonTry);
        circularProgressBar = findViewById(R.id.circularProgressBar);

        sc = getIntent().getIntExtra("score", 0);
        if (sc==6)
            sc=5;
        circularProgressBar.setProgress(sc);
        result.setText((sc * 100 / 5) + "%");

        saveScoreToFirestore(sc);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), classement.class));
                finish();
            }
        });

        trya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Question1.class));
                finish();
            }
        });
    }

    private void saveScoreToFirestore(int score) {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getEmail();
            String userName = currentUser.getDisplayName() != null ? currentUser.getDisplayName() : "Anonymous";

            Map<String, Object> scoreData = new HashMap<>();
            scoreData.put("userId", userId);
            scoreData.put("userName", userName);
            scoreData.put("score", score);
            scoreData.put("timestamp", System.currentTimeMillis());

            db.collection("scores")
                    .add(scoreData)
                    .addOnSuccessListener(documentReference -> {
                        System.out.println("Score added with ID: " + documentReference.getId());
                    })
                    .addOnFailureListener(e -> {
                        System.err.println("Error adding score: " + e.getMessage());
                    });
        } else {
            System.err.println("User not logged in. Unable to save score.");
        }
    }
}