package com.sbm.evimcebimde.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sbm.evimcebimde.R;
import com.sbm.evimcebimde.models.PostModel;

import java.util.List;
import java.util.zip.Inflater;

public class CustomAdapter extends BaseAdapter {
    List<PostModel> postList;
    Context context;
    LayoutInflater inflater;

    public CustomAdapter(Context context,List<PostModel> postList)
    {
        this.context=context;
        this.postList=postList;


    }

   @Override
    public int getCount() {
        return postList.size();
    }

    @Override
    public Object getItem(int position) {
        return postList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;

        view=View.inflate(context, R.layout.post_list,null);

        TextView title;

        title=view.findViewById(R.id.postTitle);
        title.setText(postList.get(position).getPostTitle());

        ImageView innerimg=view.findViewById(R.id.postImageView);
        innerimg.setImageBitmap(postList.get(position).getPostImage().get(0));

        inflater=LayoutInflater.from(context.getApplicationContext());




        return view;
    }
}
