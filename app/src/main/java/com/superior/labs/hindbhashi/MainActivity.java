package com.superior.labs.hindbhashi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseMessaging.getInstance().subscribeToTopic("SHORT_NEWS");

        TabLayout tabLayout = findViewById(R.id.homeTabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Top 100"));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorSecondary));
        tabLayout.addTab(tabLayout.newTab().setText("Explore"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        if (getIntent()!=null && getIntent().hasExtra("postId")){

                String k = getIntent().getExtras().getString("postId");
                Intent intent = new Intent(MainActivity.this, ViewPostActivity.class);
                intent.putExtra("id",k);
                startActivity(intent);
        }

        final ViewPager viewPager = findViewById(R.id.home_pager);

        final HomePagesAdapter adapter = new HomePagesAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
