package com.cjwilliams24680.seatgeeksearch;

import android.app.Application;

import com.cjwilliams24680.seatgeeksearch.dagger.DaggerManager;

/**
 * Created by chris on 4/12/18.
 */

public class SearchApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerManager.initApplicationComponent(this);

        // Prevents some crashes when flowable comes back after subscriber disconnects
//        RxJavaPlugins.setErrorHandler(e -> {});
    }
}
