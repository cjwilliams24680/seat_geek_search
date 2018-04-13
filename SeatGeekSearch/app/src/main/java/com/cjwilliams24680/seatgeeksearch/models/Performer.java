package com.cjwilliams24680.seatgeeksearch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chris on 4/11/18.
 *
 * There are a lot more fields here but I don't have a use for them right now
 */

public class Performer {

    @Expose
    @SerializedName("primary")
    boolean isPrimary;

    @Expose
    @SerializedName("genres")
    List<Genre> genres;

    public Performer() { }

    public boolean isPrimary() {
        return isPrimary;
    }

    public List<Genre> getGenres() {
        return genres;
    }

}
