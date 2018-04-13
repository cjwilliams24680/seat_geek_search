package com.cjwilliams24680.seatgeeksearch.dagger;

import dagger.Subcomponent;

/**
 * Created by chris on 4/12/18.
 */

@Subcomponent(
        modules = ActivityModule.class
)
public interface ApplicationComponent {
    ActivityComponent plus(ActivityModule activityModule);
}
