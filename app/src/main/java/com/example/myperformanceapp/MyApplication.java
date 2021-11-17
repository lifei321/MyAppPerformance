package com.example.myperformanceapp;


import android.app.Application;
import android.os.AsyncTask;

import com.caton.ANRWatchDog;
import com.caton.MyCaton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApplication extends Application {


    @Override
    public void onCreate() {

        super.onCreate();

        MyCaton.init();

        new ANRWatchDog().startWork();

        initThreadPool();
    }

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
