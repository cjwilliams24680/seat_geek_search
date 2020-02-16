package com.cjwilliams24680.seatgeeksearch.network.models

import com.google.gson.annotations.SerializedName

/**
 * Created by chris on 4/11/18.
 */
data class EventSearchMetadata(
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("page")
    val page: Int
)
