package com.cjwilliams24680.seatgeeksearch.dagger;

import android.app.Application;

/**
 * Created by chris on 4/12/18.
 */

public class DaggerManager {

    private static ApplicationComponent applicationComponent;

    public static void initApplicationComponent(Application application) {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(application))
                .build();
    }

    public static ApplicationComponent getApplicationComponent() {
        if (applicationComponent == null) {
            throw new IllegalStateException("component has not been initialized yet!");
        }

        return applicationComponent;
    }
}
