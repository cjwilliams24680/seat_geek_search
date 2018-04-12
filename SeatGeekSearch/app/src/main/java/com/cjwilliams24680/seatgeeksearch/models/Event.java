package com.cjwilliams24680.seatgeeksearch.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.vimeo.stag.UseStag;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZonedDateTime;

import java.util.List;

/**
 * Created by chris on 4/11/18.
 */

@UseStag
public class Event {

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
    @NonNull
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

    @NonNull
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
}
