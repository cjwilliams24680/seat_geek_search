package com.cjwilliams24680.seatgeeksearch.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vimeo.stag.UseStag;

import java.util.List;

/**
 * Created by chris on 4/11/18.
 */

@UseStag
public class EventSearchResponse {

    @Expose
    @SerializedName("meta")
    @NonNull
    EventSearchMetadata metaData;

    @Expose
    @SerializedName("events")
    @NonNull
    List<Event> events;

    public EventSearchResponse() { }

    @NonNull
    public EventSearchMetadata getMetaData() {
        return metaData;
    }

    @NonNull
    public List<Event> getEvents() {
        return events;
    }

}
