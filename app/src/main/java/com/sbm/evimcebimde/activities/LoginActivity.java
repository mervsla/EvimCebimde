package com.sbm.evimcebimde.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sbm.evimcebimde.R;

public class LoginActivity extends AppCompatActivity {

    EditText emailEt,passwordEt;
    Button loginBtn,registerBtn;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        init();

        if(mAuth.getCurrentUser() != null)
        {
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }
    void init()
    {
        emailEt=findViewById(R.id.login_emailEt);
        passwordEt=findViewById(R.id.login_passwordEt);
        loginBtn=findViewById(R.id.login_loginBtn);
        registerBtn=findViewById(R.id.login_resigterBtn);

        mAuth=FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login(emailEt.getText().toString(),passwordEt.getText().toString());

            }
        });

    }

    void login(String email,String password)
    {
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            Intent intent =new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Boyle bir kullanici bulunamadi !", Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }

}
