package com.dddg.project_dddg;

import android.app.AppComponentFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class FragmentFrame extends AppCompatActivity {
    FragmentHome fragmentHome;
    FragmentCommunity fragmentCommunity;
    FragmentNews fragmentNews;
    FragmentMyteam fragmentMyteam;
    FragmentOption fragmentOption;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottombar);
        bottomNavigationView.setOnNavigationItemSelectedListener(onBottomNavItemSelectedListener);
        fragmentHome = FragmentHome.getInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_frame,fragmentHome).commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener onBottomNavItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.menu_home: fragmentHome = FragmentHome.getInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame,fragmentHome).commit();
                    break;
                case R.id.menu_community: fragmentCommunity = FragmentCommunity.getInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame,fragmentCommunity).commit();
                    break;
                case R.id.menu_news:fragmentNews = FragmentNews.getInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame,fragmentNews).commit();
                    break;
                case R.id.menu_myteam:fragmentMyteam = FragmentMyteam.getInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame,fragmentMyteam).commit();
                    break;
                case R.id.menu_option:fragmentOption = FragmentOption.getInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame,fragmentOption).commit();
                    break;
                default: break;
            }


            return true;
        }
    };


}
