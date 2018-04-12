package com.cjwilliams24680.seatgeeksearch.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vimeo.stag.UseStag;

/**
 * Created by chris on 4/11/18.
 */

@UseStag
public class Venue {

    @Expose
    @SerializedName("url")
    String url;

    @Expose
    @SerializedName("address")
    String address;

    @Expose
    @SerializedName("extended_address")
    String extendedAddress;

    @Expose
    @SerializedName("num_upcoming_events")
    int numberOfUpcomingEvents;

    @Expose
    @SerializedName("id")
    @NonNull
    Integer id;

    public Venue() { }

    public String getUrl() {
        return url;
    }

    public String getAddress() {
        return address;
    }

    public String getExtendedAddress() {
        return extendedAddress;
    }

    public int getNumberOfUpcomingEvents() {
        return numberOfUpcomingEvents;
    }

    @NonNull
    public Integer getId() {
        return id;
    }
}
