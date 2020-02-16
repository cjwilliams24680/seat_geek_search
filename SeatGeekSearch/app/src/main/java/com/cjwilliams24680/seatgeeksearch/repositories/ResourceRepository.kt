package com.cjwilliams24680.seatgeeksearch.repositories

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

class ResourceRepository @Inject constructor(private val context: Context) {

    fun getString(@StringRes stringRes: Int) = context.getString(stringRes)

    fun getString(@StringRes stringRes: Int, vararg formatArgs: Any) = context.getString(stringRes, formatArgs)

}
