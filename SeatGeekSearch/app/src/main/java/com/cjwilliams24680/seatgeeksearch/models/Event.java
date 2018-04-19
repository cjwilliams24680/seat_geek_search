package com.cjwilliams24680.seatgeeksearch.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.List;

/**
 * Created by chris on 4/11/18.
 */

public class Event implements Parcelable {

    @Expose
    @SerializedName("venue")
    @NonNull
    Venue venue;

    @Expose
    @SerializedName("announce_date")
    @JsonAdapter(LocalDateTimeAdapter.class)
    @NonNull
    LocalDateTime announceDate;

    @Expose
    @SerializedName("datetime_tbd")
    boolean isDatetimeTbd;

    @Expose
    @SerializedName("short_title")
    @NonNull
    String shortTitle;

    @Expose
    @SerializedName("popularity")
    double popularity;

    @Expose
    @SerializedName("stats")
    @NonNull
    EventStats stats;

    @Expose
    @SerializedName("id")
    @NonNull
    Integer id;

    @Expose
    @SerializedName("visible_until_utc")
    @JsonAdapter(UtcDateTimeAdapter.class)
    @NonNull
    ZonedDateTime visibleUntilUtc;

    @Expose
    @SerializedName("datetime_utc")
    @JsonAdapter(UtcDateTimeAdapter.class)
    ZonedDateTime dateTimeUtc;

    @Expose
    @SerializedName("performers")
    @NonNull
    List<Performer> performerList;

    @Expose
    @SerializedName("datetime_local")
    @JsonAdapter(LocalDateTimeAdapter.class)
    @NonNull
    LocalDateTime dateTimeLocal;

    @Expose
    @SerializedName("title")
    @NonNull
    String title;

    @Expose
    @SerializedName("url")
    @NonNull
    String url;

    @Expose
    @SerializedName("type")
    @NonNull
    String type;

    public Event() { }

    protected Event(Parcel in) {
        venue = in.readParcelable(Venue.class.getClassLoader());
        isDatetimeTbd = in.readByte() != 0;
        shortTitle = in.readString();
        popularity = in.readDouble();
        stats = in.readParcelable(EventStats.class.getClassLoader());
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        performerList = in.createTypedArrayList(Performer.CREATOR);
        title = in.readString();
        url = in.readString();
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(venue, flags);
        dest.writeByte((byte) (isDatetimeTbd ? 1 : 0));
        dest.writeString(shortTitle);
        dest.writeDouble(popularity);
        dest.writeParcelable(stats, flags);
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeTypedList(performerList);
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @NonNull
    public Venue getVenue() {
        return venue;
    }

    @NonNull
    public LocalDateTime getAnnounceDate() {
        return announceDate;
    }

    public boolean isDatetimeTbd() {
        return isDatetimeTbd;
    }

    @NonNull
    public String getShortTitle() {
        return shortTitle;
    }

    public double getPopularity() {
        return popularity;
    }

    @NonNull
    public EventStats getStats() {
        return stats;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    @NonNull
    public ZonedDateTime getVisibleUntilUtc() {
        return visibleUntilUtc;
    }

    public ZonedDateTime getDateTimeUtc() {
        return dateTimeUtc;
    }

    @NonNull
    public List<Performer> getPerformerList() {
        return performerList;
    }

    @NonNull
    public LocalDateTime getDateTimeLocal() {
        return dateTimeLocal;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    @NonNull
    public String getType() {
        return type;
    }

    public boolean isVisible() {
        return ZonedDateTime.now().isBefore(visibleUntilUtc);
    }

    public String getShortDateTime() {
        return DateTimeFormatter.ofPattern("MM/dd hh:mm a").format(dateTimeLocal);
    }

    public String getLongDateTime() {
        return DateTimeFormatter.ofPattern("EEE MMM dd, yyyy hh:mm a").format(dateTimeLocal);
    }
}
