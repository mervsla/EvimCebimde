package com.sbm.evimcebimde.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.sbm.evimcebimde.R;
import com.sbm.evimcebimde.fragments.HomeFragment;
import com.sbm.evimcebimde.fragments.ProfileFragment;
import com.sbm.evimcebimde.fragments.ShareFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        init();


    }

    void init()
    {   BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        navigationView.setOnNavigationItemSelectedListener(this);


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment selectedFragment=null;

        if (id == R.id.nav_home) {
            // Handle the camera action
            selectedFragment=new HomeFragment();
        } else if (id == R.id.nav_profile) {
            selectedFragment=new ProfileFragment();

        }else if (id == R.id.nav_sharePost) {
            selectedFragment=new ShareFragment();
        }

        if(selectedFragment!=null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, selectedFragment).commit();
        }

        return true;

    }
}
