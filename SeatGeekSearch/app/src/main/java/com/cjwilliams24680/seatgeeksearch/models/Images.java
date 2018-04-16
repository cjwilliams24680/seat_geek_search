package com.cjwilliams24680.seatgeeksearch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chris on 4/16/18.
 */

public class Images {

    @Expose
    @SerializedName("huge")
    String huge;

    public Images() { }

    public String getHuge() {
        return huge;
    }
}
