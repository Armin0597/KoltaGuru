package com.koltaapp.koltaguru;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Animation app_splash;
    Animation bottom_to_top;
    TextView app_titles,app_subtitles;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //load animation
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        bottom_to_top = AnimationUtils.loadAnimation(this,R.anim.bottom_to_top);

        //load element
        app_titles = findViewById(R.id.app_titles);
        app_subtitles = findViewById(R.id.app_subtitles);

        //run animation
        app_titles.startAnimation(app_splash);
        app_subtitles.startAnimation(bottom_to_top);

        //setting timer untuk 2 detik
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //merubah activity ke activity lain
                Intent gosignin = new Intent(MainActivity.this, GetStartedAct.class);
                startActivity(gosignin);
                finish();
            }
        },2000);

        //getUsernameLocal();

    }
//    public  void getUsernameLocal(){
//        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
//        username_key_new = sharedPreferences.getString(username_key, "");
//        if(username_key_new.isEmpty()){
//            //setting timer untuk 2 detik
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    //merubah activity ke activity lain
//                    Intent gosignin = new Intent(MainActivity.this, GetStartedAct.class);
//                    startActivity(gosignin);
//                    finish();
//                }
//            },2000);
//
//        }else{
//            //setting timer untuk 2 detik
////            Handler handler = new Handler();
////            handler.postDelayed(new Runnable() {
////                @Override
////                public void run() {
////                    //merubah activity ke activity lain
////                    Intent gogethome = new Intent(MainActivity.this, HomePageTeacherActivity.class);
////                    startActivity(gogethome);
////                    finish();
////                }
////            },2000);
//        }
//    }
}