package com.nadxlib.robi_all_intregation.advert;

import android.app.Application;
import android.content.Context;

public class AppConfig extends Application {

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();

        context =  getApplicationContext();

    }


    public static Context getContext(){
        return context;
    }



}
