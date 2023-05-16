package com.example.facturasapp;

import android.content.Context;

import com.example.facturasapp.database.AppDatabase;

public class Application extends android.app.Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        AppDatabase.getDbInstance(getApplicationContext());
    }
}
