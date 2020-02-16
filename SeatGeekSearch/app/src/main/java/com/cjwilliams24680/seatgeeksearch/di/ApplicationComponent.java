package com.cjwilliams24680.seatgeeksearch.di;

import com.cjwilliams24680.seatgeeksearch.ui.activities.HomeActivity;
import com.cjwilliams24680.seatgeeksearch.ui.screens.search.SearchFragment;
import com.cjwilliams24680.seatgeeksearch.ui.screens.searchDetail.SearchDetailFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by chris on 4/12/18.
 */

@Singleton
@Component(
        modules = { ViewModelModule.class, ApplicationModule.class }
)
public interface ApplicationComponent {
    void inject(HomeActivity activity);
    void inject(SearchFragment fragment);
    void inject(SearchDetailFragment fragment);

    ActivityComponent plus(ActivityModule activityModule);
}
