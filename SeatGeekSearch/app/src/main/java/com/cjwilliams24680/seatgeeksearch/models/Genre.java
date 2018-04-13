package com.cjwilliams24680.seatgeeksearch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chris on 4/11/18.
 *
 * There are a lot of options here. I picked the two that were most useful,
 * but I can revisit the list if they don't work
 */

public class Genre {

    @Expose
    @SerializedName("fb_600_315")
    boolean smallImage;

    @Expose
    @SerializedName("1200x627")
    boolean largeImage;

    public Genre() { }

    public boolean isSmallImage() {
        return smallImage;
    }

    public boolean isLargeImage() {
        return largeImage;
    }

}
