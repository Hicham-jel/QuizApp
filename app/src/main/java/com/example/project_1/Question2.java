package com.example.project_1;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Question2 extends AppCompatActivity {
    Button next;
    RadioGroup rg;
    String tp = "question2";
    RadioButton rb, option1, option2, option3, option4;
    int score;
    TextView Timer,questionText;
    String correct="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question2);
        next=findViewById(R.id.next);
        questionText = findViewById(R.id.quest);
        option1 = findViewById(R.id.radioButton1);
        option2 = findViewById(R.id.radioButton2);
        option3 = findViewById(R.id.radioButton3);
        option4 = findViewById(R.id.radioButton4);
        rg=findViewById(R.id.RadioGroup2);
        Intent i2=getIntent();
        score=i2.getIntExtra("score",0);
        Timer = findViewById(R.id.TimerView1);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://quizapp-dee18-default-rtdb.firebaseio.com/");
        databaseReference.child(tp).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String getquestion = snapshot.child("question").getValue(String.class);
                final String getop1 = snapshot.child("op1").getValue(String.class);
                final String getop2 = snapshot.child("op2").getValue(String.class);
                final String getop3 = snapshot.child("op3").getValue(String.class);
                final String getop4 = snapshot.child("op4").getValue(String.class);
                correct = snapshot.child("correct").getValue(String.class);

                if (getquestion != null && getop1 != null && getop2 != null && getop3 != null && getop4 != null && correct != null) {
                    questionText.setText(getquestion);
                    option1.setText(getop1);
                    option2.setText(getop2);
                    option3.setText(getop3);
                    option4.setText(getop4);
                } else {
                    Toast.makeText(Question2.this, "Erreur: Une question ou une option manque.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Question2.this, "Erreur lors du chargement des questions.", Toast.LENGTH_SHORT).show();
            }
        });

        long timerDuration = TimeUnit.MINUTES.toMillis(1);
        long ticksInterval = 30;

        new CountDownTimer(timerDuration, ticksInterval) {
            @Override
            public void onTick(long millisUntilFinished) {

                String TimerText = String.format(Locale.getDefault(), "%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)),
                        millisUntilFinished % 60);
                Timer.setText(TimerText);
            }

            @Override
            public void onFinish() {
                Intent i1 = new Intent(getApplicationContext(), Question3.class);
                i1.putExtra("score", score);
                startActivity(i1);
            }

        }.start();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rg.getCheckedRadioButtonId()==-1){
                    Toast.makeText(getApplicationContext(), "Veuillez cocher une réponse", Toast.LENGTH_SHORT).show();
                }
                else {
                    rb=findViewById(rg.getCheckedRadioButtonId());
                    if (rb.getText().toString().equals(correct)) score+=1;
                    Intent i1=new Intent(getApplicationContext(), Question3.class);
                    i1.putExtra("score",score);
                    startActivity(i1);
                    finish();
                }
            }
        });
    }
}