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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sbm.evimcebimde.R;

public class RegisterActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference mRef;
    EditText emailEt,passwordEt,passwordConEt,nameEt,surnameEt,phoneEt,addressEt;
    Button registerBtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        init();
    }
    void init()
    {
        database= FirebaseDatabase.getInstance();
        emailEt=findViewById(R.id.register_email);
        passwordEt=findViewById(R.id.register_pass);
        passwordConEt=findViewById(R.id.register_pass_con_et);
        registerBtn=findViewById(R.id.register_regBtn);

        nameEt=findViewById(R.id.nameEt);
        surnameEt=findViewById(R.id.surnameEt);
        phoneEt=findViewById(R.id.phoneEt);
        addressEt=findViewById(R.id.addressEt);

        mAuth=FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Register on Click

                register(emailEt.getText().toString(),passwordEt.getText().toString());

            }
        });

    }

    void register(String email,String password)
    {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);

                    loadProfile(nameEt.getText().toString(),surnameEt.getText().toString(),phoneEt.getText().toString(),addressEt.getText().toString());
                }
                //onComple
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    void loadProfile( String name, String surname, String phone, String address)
    {
        mRef=database.getReference("/Users/"+mAuth.getUid());
        mRef.child("Name").setValue(name);
        mRef.child("Surname").setValue(surname);
        mRef.child("Phone").setValue(phone);
        mRef.child("Address").setValue(address);

    }
}
