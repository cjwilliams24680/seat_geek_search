package com.cjwilliams24680.seatgeeksearch.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by chris on 4/11/18.
 */
@Parcelize
data class Venue(
        @SerializedName("url") val url: String?,
        @SerializedName("address") val address: String?,
        @SerializedName("extended_address") var extendedAddress: String?,
        @SerializedName("num_upcoming_events") val numberOfUpcomingEvents: Int,
        @SerializedName("id") val id: Long,
        @SerializedName("name_v2") val name: String?,
        @SerializedName("display_location") val displayLocation: String? = null
) : Parcelable