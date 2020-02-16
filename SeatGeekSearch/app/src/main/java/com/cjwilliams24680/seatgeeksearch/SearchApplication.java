package com.cjwilliams24680.seatgeeksearch;

import android.app.Application;

import com.cjwilliams24680.seatgeeksearch.di.DaggerManager;
import com.jakewharton.threetenabp.AndroidThreeTen;

import io.reactivex.plugins.RxJavaPlugins;

/**
 * Created by chris on 4/12/18.
 */

public class SearchApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
        DaggerManager.initApplicationComponent(this);

        // Prevents some crashes when flowable comes back after subscriber disconnects
        RxJavaPlugins.setErrorHandler(e -> {});
    }
}
