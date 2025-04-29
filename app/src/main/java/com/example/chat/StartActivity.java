package com.example.chat;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class StartActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    chat chat;
    map map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        EdgeToEdge.enable (this);
        setContentView (R.layout.start_activity);

        chat = new chat();
        map = new map();

        bottomNavigationView = findViewById(R.id.nav_menu);



        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.map){
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, map).commit();
                }
                if(item.getItemId()==R.id.chat){
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame, chat).commit();
                }
                return true;
            }
        });
    }
}