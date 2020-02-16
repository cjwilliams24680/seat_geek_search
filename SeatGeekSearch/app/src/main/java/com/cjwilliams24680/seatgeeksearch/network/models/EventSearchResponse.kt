package com.cjwilliams24680.seatgeeksearch.network.models

import com.google.gson.annotations.SerializedName

/**
 * Created by chris on 4/11/18.
 */
data class EventSearchResponse(
    @SerializedName("meta")
    var metaData: EventSearchMetadata,
    @SerializedName("events")
    var events: List<Event>
)
