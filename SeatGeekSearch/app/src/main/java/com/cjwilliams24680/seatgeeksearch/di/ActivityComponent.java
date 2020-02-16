package com.cjwilliams24680.seatgeeksearch.di;

import com.cjwilliams24680.seatgeeksearch.ui.screens.searchDetail.SearchDetailFragment;
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
        // unused right now
}
