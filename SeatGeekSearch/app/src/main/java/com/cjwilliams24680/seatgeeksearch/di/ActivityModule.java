package com.cjwilliams24680.seatgeeksearch.di;

import android.app.Activity;

import dagger.Module;

/**
 * Created by chris on 4/12/18.
 *
 * Injects items which live at the Activity level
 *
 * Unused but just an example of a subcomponent module
 */

@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

}
