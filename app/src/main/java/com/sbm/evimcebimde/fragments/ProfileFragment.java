package com.sbm.evimcebimde.fragments;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sbm.evimcebimde.R;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    View view;
    EditText nameEt,surnameEt,phoneEt,emailEt,addressEt, YeniParola, YeniParolaTekrar;
    Button phoneButton, emailButton, addressButton, saveButton, passwordBtn, ParolaButton;
    ImageView imageView4;
    ConstraintLayout passwordContainer;

    FirebaseDatabase database;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    FirebaseStorage storage;
    StorageReference mStoreRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //return inflater.inflate(R.layout.fragment_profile, container, false);
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        init();

        return view;
    }
    void init() {
        nameEt = view.findViewById(R.id.nameEt);
        surnameEt = view.findViewById(R.id.surnameEt);
        phoneEt = view.findViewById(R.id.phoneEt);
YeniParola= view. findViewById(R.id.YeniParola);
YeniParolaTekrar= view. findViewById(R.id. YeniParolaTekrar);
        emailEt = view.findViewById(R.id.emailEt);
imageView4= view.findViewById(R.id.imageView4);
        addressEt = view.findViewById(R.id.addressEt);

passwordContainer= view.findViewById(R.id.passwordContainer);
        passwordBtn= view.findViewById(R.id.passwordBtn);
        phoneButton = view.findViewById(R.id.phoneButton);
        emailButton = view.findViewById(R.id.emailButton);
ParolaButton = view. findViewById(R.id.ParolaButton);
        addressButton = view.findViewById(R.id.addressButton);
        saveButton = view.findViewById(R.id.saveButton);

        ParolaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(YeniParola.getText().toString().equals(YeniParolaTekrar.getText().toString())){

                    changePassword(YeniParola.getText().toString());
                }

            }
        });

        passwordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
passwordContainer.setVisibility(View.VISIBLE);
            }
        });
        passwordContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordContainer.setVisibility(View.INVISIBLE);

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveProfile(
                        phoneEt.getText().toString(),
                        emailEt.getText().toString(),
                        addressEt.getText().toString()
                );

            }
        });
        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("/Users/" + mAuth.getUid());

        storage = FirebaseStorage.getInstance();
        mStoreRef = storage.getReference("/Users/" + mAuth.getUid());
        loadProfile();
    }
    void saveProfile(String phone,String email,String address)
    {

        mRef.child("Phone").setValue(phone);
        mRef.child("Email").setValue(email);
        mRef.child("Address").setValue(address);

    }

    void loadProfile() {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                phoneEt.setText(dataSnapshot.child("Phone").getValue().toString());
                emailEt.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                addressEt.setText(dataSnapshot.child("Address").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(
                        getContext(),
                        "Bilgiler yuklenemedi ! :" + databaseError.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });


    }

    void changePassword(String newPass)
    {
        FirebaseAuth auth=FirebaseAuth.getInstance();

        auth.getCurrentUser().updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getContext(),"Sifre Degişti",Toast.LENGTH_LONG).show();
                passwordContainer.setVisibility(View.INVISIBLE);
            }
        });
    }

    }
