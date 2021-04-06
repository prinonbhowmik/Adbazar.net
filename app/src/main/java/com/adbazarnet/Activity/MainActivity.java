package com.adbazarnet.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.adbazarnet.Fragments.BidsFragment;
import com.adbazarnet.Fragments.HomeFragment;
import com.adbazarnet.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        sharedPreferences = getSharedPreferences("MyRef",MODE_PRIVATE);
        int id = sharedPreferences.getInt("id",0);
        if (id==0){
            navigationView.getMenu().removeItem(R.id.logout);
        }
        if(id!=0){
            navigationView.getMenu().removeItem(R.id.login);
        }

        FragmentTransaction home = getSupportFragmentManager().beginTransaction();
        home.replace(R.id.fragment_container, new HomeFragment());
        home.commit();
    }

    private void init() {
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                break;
            case R.id.home:
                FragmentTransaction home = getSupportFragmentManager().beginTransaction();
                home.replace(R.id.fragment_container, new HomeFragment());
                home.commit();
                drawerLayout.closeDrawers();
                break;
            case R.id.bids:
                FragmentTransaction bids = getSupportFragmentManager().beginTransaction();
                bids.replace(R.id.fragment_container, new BidsFragment());
                bids.commit();
                drawerLayout.closeDrawers();
                break;
            case R.id.chat:
                drawerLayout.closeDrawers();
                break;
            case R.id.account:
                drawerLayout.closeDrawers();
                break;
            case R.id.logout:
                drawerLayout.closeDrawers();
                break;


        }
        return false;
    }
}