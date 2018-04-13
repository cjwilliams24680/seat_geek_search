package com.cjwilliams24680.seatgeeksearch.dagger;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by chris on 4/12/18.
 */

@Singleton
@Component(
        modules = {
                ApplicationModule.class
        }
)
public interface ActivityComponent {
}
