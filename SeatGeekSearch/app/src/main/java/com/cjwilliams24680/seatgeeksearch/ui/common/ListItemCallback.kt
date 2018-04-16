package com.cjwilliams24680.seatgeeksearch.ui.common

/**
 * Created by chris on 4/15/18.
 */
interface ListItemCallback<in T> {
    fun onItemSelected(item: T)
}