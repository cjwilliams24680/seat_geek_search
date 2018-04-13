package com.cjwilliams24680.seatgeeksearch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chris on 4/11/18.
 */

public class EventStats {

    @Expose
    @SerializedName("listing_count")
    int listingCount;

    @Expose
    @SerializedName("highest_price")
    int highestPrice;

    public EventStats() { }

    public int getListingCount() {
        return listingCount;
    }

    public int getHighestPrice() {
        return highestPrice;
    }

}
