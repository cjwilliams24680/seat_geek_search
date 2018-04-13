package com.cjwilliams24680.seatgeeksearch.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chris on 4/11/18.
 */

public class EventSearchMetadata {

    @Expose
    @SerializedName("per_page")
    @NonNull
    Integer perPage;

    @Expose
    @SerializedName("total")
    @NonNull
    Integer total;

    @Expose
    @SerializedName("page")
    @NonNull
    Integer page;

    public EventSearchMetadata() { }

    @NonNull
    public Integer getPerPage() {
        return perPage;
    }

    @NonNull
    public Integer getTotal() {
        return total;
    }

    @NonNull
    public Integer getPage() {
        return page;
    }
}
