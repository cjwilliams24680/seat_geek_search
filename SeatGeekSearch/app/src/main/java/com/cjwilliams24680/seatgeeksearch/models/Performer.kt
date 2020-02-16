package com.cjwilliams24680.seatgeeksearch.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by chris on 4/11/18.
 *
 * There are a lot more fields here but I don't have a use for them right now
 */
@Parcelize
data class Performer(
        @SerializedName("primary")
        val isPrimary: Boolean,
        @SerializedName("image")
        val image: String?
) : Parcelable
