package com.cjwilliams24680.seatgeeksearch.repositories

import com.cjwilliams24680.seatgeeksearch.BuildConfig
import com.cjwilliams24680.seatgeeksearch.models.CloudUtils
import com.cjwilliams24680.seatgeeksearch.models.Event
import com.cjwilliams24680.seatgeeksearch.network.SeatGeekApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepository @Inject constructor(private val seatGeekApi: SeatGeekApi) {

    fun getActiveEventsRx(searchQuery: String) =
            seatGeekApi.searchEventsFlowable(searchQuery, 20, BuildConfig.SEAT_GEEK_CLIENT_ID)
                    .flatMapSingle { response -> CloudUtils.filterExpiredEvents(response.events) }

    suspend fun getActiveEvents(searchQuery: String) =
            seatGeekApi.searchEvents(searchQuery, 20, BuildConfig.SEAT_GEEK_CLIENT_ID)

}
