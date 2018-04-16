package com.cjwilliams24680.seatgeeksearch.dagger;

import com.cjwilliams24680.seatgeeksearch.ui.screens.SearchDetailFragment;
import com.cjwilliams24680.seatgeeksearch.ui.screens.search.SearchFragment;

import dagger.Subcomponent;

/**
 * Created by chris on 4/12/18.
 */

@Subcomponent(
        modules = {
                ActivityModule.class
        }
)
public interface ActivityComponent {
        void inject(SearchFragment fragment);
        void inject(SearchDetailFragment fragment);
}
