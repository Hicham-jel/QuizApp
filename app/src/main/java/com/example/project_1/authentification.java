package com.example.project_1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class authentification extends AppCompatActivity {
   EditText email,password,password1,username;
   FirebaseAuth MyAuth;
   Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_authentification);
        email=findViewById(R.id.emailA);
        password=findViewById(R.id.passwordA);
        password1=findViewById(R.id.passwordAA);
        MyAuth= FirebaseAuth.getInstance();
        username=findViewById(R.id.nameuser);
        register=findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=email.getText().toString();
                String pass=password.getText().toString();
                String pass1=password1.getText().toString();
                String name=username.getText().toString();
                if (TextUtils.isEmpty(mail)||TextUtils.isEmpty(pass)||TextUtils.isEmpty(pass1))
                {
                    Toast.makeText(authentification.this,"Remplir les champs avec *",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass.length()<6)
                {
                    Toast.makeText(authentification.this,"Veuillez saisir plus que 6 caractères",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!pass1.equals(pass))
                {

                    Toast.makeText(authentification.this,"Vérification incorrecte",Toast.LENGTH_SHORT).show();
                }
                SignUp(mail, pass);
            }

        });

    }
    public void SignUp(String mail,String password)
    {
        MyAuth.createUserWithEmailAndPassword(mail,password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {Toast.makeText(authentification.this,"Enregistrement réussi",Toast.LENGTH_SHORT).show();
                            Intent i2=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(i2);
                            finish();
                    }else {
                            Toast.makeText(authentification.this,"Enregistrement échouée"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }}

                });



    }

    }
