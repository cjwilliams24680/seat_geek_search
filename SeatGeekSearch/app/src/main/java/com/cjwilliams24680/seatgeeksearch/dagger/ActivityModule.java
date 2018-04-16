package com.cjwilliams24680.seatgeeksearch.dagger;

import android.app.Activity;

import java.lang.ref.WeakReference;

import dagger.Module;

/**
 * Created by chris on 4/12/18.
 *
 * Injects items which live at the Activity level
 */

@Module
public class ActivityModule {

    private final WeakReference<Activity> activity;

    public ActivityModule(Activity activity) {
        this.activity = new WeakReference<>(activity);
    }

}
