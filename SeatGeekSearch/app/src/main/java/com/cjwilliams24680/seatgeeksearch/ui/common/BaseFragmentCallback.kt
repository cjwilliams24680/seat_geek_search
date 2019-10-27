package com.cjwilliams24680.seatgeeksearch.ui.common

import androidx.annotation.StringRes
import com.cjwilliams24680.seatgeeksearch.dagger.ActivityComponent

/**
 * Created by chris on 4/15/18.
 */

interface BaseFragmentCallback {
    fun getActivityComponent() : ActivityComponent
    fun showSnackbar(@StringRes text: Int, duration: Int)
}
