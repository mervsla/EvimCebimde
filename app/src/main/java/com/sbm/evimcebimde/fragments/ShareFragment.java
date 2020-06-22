package com.sbm.evimcebimde.fragments;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sbm.evimcebimde.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;


public class ShareFragment extends Fragment {
    ImageView HomeIw;
    Button ShareBtn;
    Button galeryBtn;
    Button CameraBtn;
    EditText OdaInfo;
    EditText KriterInfo;
    EditText ÖzellikInfo;
    TextView OdaTw;
    TextView KriterTw;
    TextView ÖzellikTw;
    Button logout;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_share, container, false);

        init();
        return view;

    }


    void init()
    {
        HomeIw=view.findViewById(R.id.Homelw);
        ShareBtn=view.findViewById(R.id.ShareBtn);
        CameraBtn=view.findViewById(R.id.CameraBtn);
        galeryBtn=view.findViewById(R.id.galleryBtn);
        OdaInfo=view.findViewById(R.id.OdaInfo);
        KriterInfo=view.findViewById(R.id.KriterInfo);
        ÖzellikInfo=view.findViewById(R.id.ÖzellikInfo);
        OdaTw=view.findViewById(R.id.OdaTw);
        KriterTw=view.findViewById(R.id.KriterTw);
        ÖzellikTw=view.findViewById(R.id.ÖzellikTw);
        CameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 100);


            }
        });


        ShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ÖzellikInfo.getText().toString().equals("") ||
                        ((BitmapDrawable)HomeIw.getDrawable()).getBitmap()==null)
                {
                    Toast.makeText(getContext(),"Bilgiler Eksik",Toast.LENGTH_LONG).show();
                    return;
                }

                postImage(ÖzellikInfo.getText().toString() , ((BitmapDrawable)HomeIw.getDrawable()).getBitmap());

            }
        });

        galeryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,200);

            }
        });

    }


    void postImage(final String title, Bitmap Imagebitmap)
    {
        Toast.makeText(getContext(),"Post Ilesmemi basladi",Toast.LENGTH_LONG).show();


    ShareBtn.setEnabled(false);
        ShareBtn.setBackgroundColor(Color.GRAY);
        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        FirebaseStorage mStorage=FirebaseStorage.getInstance();
        StorageReference storageRef=mStorage.getReference("PostsImages/"+ FirebaseAuth.getInstance().getUid()+"/"+timestamp.getTime());


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Imagebitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                ShareBtn.setEnabled(true);
                ShareBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                Toast.makeText(getContext(),"Bir hara olustu",Toast.LENGTH_LONG).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                FirebaseDatabase database= FirebaseDatabase.getInstance();
                DatabaseReference mRef=database.getReference("/Posts/" + FirebaseAuth.getInstance().getUid()).push();
                mRef.child("Title").setValue(title);
                mRef.child("ImageId").setValue(timestamp.getTime());

                Toast.makeText(getContext(),"Post Paylasildi",Toast.LENGTH_LONG).show();

                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer, new HomeFragment()).commit();

            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK) {

            switch (requestCode) {

                case 100:
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    HomeIw.setImageBitmap(photo);



                    break;
                case 200:

                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        HomeIw.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();

                    }

                    break;


            }

        }


    }

}
