package com.cjwilliams24680.seatgeeksearch.network.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by chris on 4/11/18.
 */
@Parcelize
data class EventStats(
        @SerializedName("listing_count")
        val listingCount: Int,
        @SerializedName("lowest_price")
        val lowestPrice: Int
) : Parcelable
