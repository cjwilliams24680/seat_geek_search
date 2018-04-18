package com.cjwilliams24680.seatgeeksearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chris on 4/11/18.
 */

public class EventStats implements Parcelable {

    @Expose
    @SerializedName("listing_count")
    int listingCount;

    @Expose
    @SerializedName("lowest_price")
    int lowestPrice;

    public EventStats() { }

    protected EventStats(Parcel in) {
        listingCount = in.readInt();
        lowestPrice = in.readInt();
    }

    public static final Creator<EventStats> CREATOR = new Creator<EventStats>() {
        @Override
        public EventStats createFromParcel(Parcel in) {
            return new EventStats(in);
        }

        @Override
        public EventStats[] newArray(int size) {
            return new EventStats[size];
        }
    };

    public int getListingCount() {
        return listingCount;
    }

    public int getLowestPrice() {
        return lowestPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(listingCount);
        dest.writeInt(lowestPrice);
    }
}
