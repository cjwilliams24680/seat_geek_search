package com.cjwilliams24680.seatgeeksearch.models

import android.os.Parcelable
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

/**
 * Created by chris on 4/11/18.
 */
@Parcelize
data class Event(
        @SerializedName("venue")
        val venue: Venue,

        @SerializedName("announce_date")
        @JsonAdapter(LocalDateTimeAdapter::class)
        val announceDate: LocalDateTime,

        @SerializedName("datetime_tbd")
        val isDatetimeTbd: Boolean,

        @SerializedName("short_title")
        val shortTitle: String,

        @SerializedName("popularity")
        val popularity: Double,

        @SerializedName("stats")
        val stats: EventStats,

        @SerializedName("id")
        val id: Long,

        @SerializedName("visible_until_utc")
        @JsonAdapter(UtcDateTimeAdapter::class)
        val visibleUntilUtc: ZonedDateTime,

        @SerializedName("datetime_utc")
        @JsonAdapter(UtcDateTimeAdapter::class)
        val dateTimeUtc: ZonedDateTime,

        @SerializedName("performers")
        val performerList: List<Performer>,

        @SerializedName("datetime_local")
        @JsonAdapter(LocalDateTimeAdapter::class)
        val dateTimeLocal: LocalDateTime,

        @SerializedName("title")
        val title: String,

        @SerializedName("url")
        val url: String,

        @SerializedName("type")
        val type: String
    ) : Parcelable {

    fun isVisible() = ZonedDateTime.now().isBefore(visibleUntilUtc)

    fun getShortDateTime(): String = DateTimeFormatter.ofPattern("MM/dd hh:mm a").format(dateTimeLocal)

    fun getLongDateTime(): String = DateTimeFormatter.ofPattern("EEE MMM dd, yyyy hh:mm a").format(dateTimeLocal)

}
