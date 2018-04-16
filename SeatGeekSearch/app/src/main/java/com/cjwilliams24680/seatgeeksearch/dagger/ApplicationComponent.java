package com.cjwilliams24680.seatgeeksearch.dagger;

import com.cjwilliams24680.seatgeeksearch.ui.activities.HomeActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by chris on 4/12/18.
 */

@Singleton
@Component(
        modules = ApplicationModule.class
)
public interface ApplicationComponent {
    void inject(HomeActivity activity);

    ActivityComponent plus(ActivityModule activityModule);
}
