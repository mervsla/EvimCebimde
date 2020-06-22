package com.sbm.evimcebimde.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sbm.evimcebimde.R;
import com.sbm.evimcebimde.adapters.CustomAdapter;
import com.sbm.evimcebimde.models.PostModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    View view;
    ListView post_List;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);

        init();
        loadPost();

        return  view;
    }

    void init()
    {
        post_List=view.findViewById(R.id.postList);

    }


    void loadPost()
    {

        final List<PostModel> itemList=new ArrayList<>();

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference mRef=database.getReference("/Posts/");


        final FirebaseStorage mStorage=FirebaseStorage.getInstance();

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (final DataSnapshot users:dataSnapshot.getChildren()) {

                    for (final DataSnapshot post:users.getChildren()) {

                        StorageReference storageRef=mStorage.getReference("PostsImages/"+ users.getKey()+"/"+post.child("ImageId").getValue());

                        final long ONE_MEGABYTE = 1024 * 1024 *5;
                        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {

                                PostModel postModel=new PostModel();
                                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);

                                postModel.setPostTitle(post.child("Title").getValue().toString());
                                postModel.addPostImage(bitmap);

                                itemList.add(postModel);

                                CustomAdapter adapter=new CustomAdapter(getContext(),itemList);
                                post_List.setAdapter(adapter);


                            }

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }



}
