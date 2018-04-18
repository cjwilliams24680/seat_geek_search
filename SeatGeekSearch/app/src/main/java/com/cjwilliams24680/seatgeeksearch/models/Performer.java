package com.cjwilliams24680.seatgeeksearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chris on 4/11/18.
 *
 * There are a lot more fields here but I don't have a use for them right now
 */

public class Performer implements Parcelable {

    @Expose
    @SerializedName("primary")
    boolean isPrimary;

    @Expose
    @SerializedName("image")
    String image;

    public Performer() { }

    protected Performer(Parcel in) {
        isPrimary = in.readByte() != 0;
        image = in.readString();
    }

    public static final Creator<Performer> CREATOR = new Creator<Performer>() {
        @Override
        public Performer createFromParcel(Parcel in) {
            return new Performer(in);
        }

        @Override
        public Performer[] newArray(int size) {
            return new Performer[size];
        }
    };

    public boolean isPrimary() {
        return isPrimary;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isPrimary ? 1 : 0));
        dest.writeString(image);
    }
}
