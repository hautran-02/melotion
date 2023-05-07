package com.example.musicplayer;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.musicplayer.fragment.CategoryManagerFragment;
import com.example.musicplayer.fragment.HomeFragment;
import com.example.musicplayer.fragment.SettingFragment;
import com.example.musicplayer.fragment.SongManagerFragment;
import com.example.musicplayer.fragment.UserManagerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_song:
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.container, new SongManagerFragment())
                                        .commit();
                                return true;
                            case R.id.navigation_category:
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.container, new CategoryManagerFragment())
                                        .commit();
                                return true;
                            case R.id.navigation_user_maneger:
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.container, new UserManagerFragment())
                                        .commit();
                                return true;
                        }
                        return false;
                    }
                });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new SongManagerFragment())
                .commit();
    }
}
