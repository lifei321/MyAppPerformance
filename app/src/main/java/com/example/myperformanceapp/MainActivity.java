package com.example.myperformanceapp;


import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        gotoAop();

        gotoSleep();
    }


    public void gotoAop() {
        Log.d("AOP--", "test---gotoAop");
    }

    private void gotoSleep() {
        Log.d("Caton--", "test---gotoSleep");

        SystemClock.sleep(2000);
    }
}