package com.example.introui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    TabLayout tabIndicator;
    Button button;
    int position = 0;
    Button btnGetStarted;
    Animation btnAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make the activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);

        //checking for myPrefs
        if (restorePrefData()){
            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }


        button = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_get_started);
        btnAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_animation);

        //ini views
        tabIndicator = findViewById(R.id.tab_indicator);

        //fill the screen
        List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Welcome Señor", "A whole new way of experiencing our services with Order It app.", R.drawable.welcome_senor));
        mList.add(new ScreenItem("Track", "Track your orders at all times of the day and night.", R.drawable.track_order));
        mList.add(new ScreenItem("Fast Delivery", "Get fast and free delivery with no extra cost just for you.", R.drawable.fast_delivery));
        mList.add(new ScreenItem("New Technology", "We innovate to bring you best ways to delivery your orders at your door step.", R.drawable.drone));
        mList.add(new ScreenItem("Future", "Find out what future holds for you!", R.drawable.ufo));

        //set viewPager
        screenPager = findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this, mList);
        screenPager.setAdapter(introViewPagerAdapter);

        //setup tabLayout with viewpager
        tabIndicator.setupWithViewPager(screenPager);

        //next button click listner
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = screenPager.getCurrentItem();
                if (position < mList.size()){
                    position++;
                    screenPager.setCurrentItem(position);
                }

                if (position == mList.size()-1){
                    //when we reach to the last screen
                    loadLastScreen();
                }
            }
        });
        //tabLayout add change listener
        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == mList.size()-1){
                    loadLastScreen();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //get started button click listener
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open main activity
                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(intent);

                //using shared prefrence
                savePrefsData();
                finish();
            }
        });

    }

    private boolean restorePrefData() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean isIntroActivityOpenedBefore = sharedPreferences.getBoolean("isIntroOpened", false);
        return isIntroActivityOpenedBefore;
    }

    private void savePrefsData() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.commit();
    }

    //show the GETSTARTED Button and hide the indicator and the next button
    private void loadLastScreen() {

        button.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);

        //adding button animation
        btnGetStarted.setAnimation(btnAnimation);
    }
}