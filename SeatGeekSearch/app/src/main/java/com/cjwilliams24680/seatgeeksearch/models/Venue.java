package com.cjwilliams24680.seatgeeksearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chris on 4/11/18.
 */

public class Venue implements Parcelable {

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
    Integer id;

    @Expose
    @SerializedName("name_v2")
    String name;

    @Expose
    @SerializedName("display_location")
    String displayLocation;

    public Venue() { }

    protected Venue(Parcel in) {
        url = in.readString();
        address = in.readString();
        extendedAddress = in.readString();
        numberOfUpcomingEvents = in.readInt();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        displayLocation = in.readString();
    }

    public static final Creator<Venue> CREATOR = new Creator<Venue>() {
        @Override
        public Venue createFromParcel(Parcel in) {
            return new Venue(in);
        }

        @Override
        public Venue[] newArray(int size) {
            return new Venue[size];
        }
    };

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

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDisplayLocation() {
        return displayLocation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(address);
        dest.writeString(extendedAddress);
        dest.writeInt(numberOfUpcomingEvents);
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(displayLocation);
    }
}
