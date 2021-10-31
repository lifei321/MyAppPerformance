package com.example.myperformanceapp;


import android.app.Application;
import android.os.AsyncTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApplication extends Application {



    public void initThreadPool() {
//        ExecutorService service = Executors.newFixedThreadPool(CORE_POOL_SIZE);
        ExecutorService service = Executors.newFixedThreadPool(5);

        service.submit(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

}
