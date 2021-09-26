package com.handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.myperformanceapp.R;

public class MyHandlerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_handler);

        Handler mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {

            }
        };


        new Thread(new Runnable() {
            @Override
            public void run() {

                Message message = Message.obtain();
                message.obj = "hello world";
                mHandler.sendMessage(message);

                Message message1 = mHandler.obtainMessage(1, "hello world two");

            }
        }).start();
    }
}