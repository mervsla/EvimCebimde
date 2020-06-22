package com.sbm.evimcebimde.models;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class PostModel {

    String postTitle;
    List<Bitmap> postImage;

    public PostModel ()
    {
        postImage=new ArrayList<>();
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public List<Bitmap> getPostImage() {
        return postImage;
    }

    public void addPostImage(Bitmap postImage) {
        this.postImage.add(postImage);
    }
}
