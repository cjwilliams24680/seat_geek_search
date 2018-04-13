package com.cjwilliams24680.seatgeeksearch.dagger;

import android.app.Application;

import java.lang.ref.WeakReference;

import dagger.Module;

/**
 * Created by chris on 4/12/18.
 *
 * Injects items which live at the application level
 */

@Module
public class ApplicationModule {

    private final WeakReference<Application> application;

    public ApplicationModule(Application application) {
        this.application = new WeakReference<>(application);
    }

}
