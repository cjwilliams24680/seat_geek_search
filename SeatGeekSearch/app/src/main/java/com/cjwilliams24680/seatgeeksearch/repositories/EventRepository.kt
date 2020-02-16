package com.cjwilliams24680.seatgeeksearch.repositories

import com.cjwilliams24680.seatgeeksearch.BuildConfig
import com.cjwilliams24680.seatgeeksearch.network.SeatGeekApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepository @Inject constructor(private val seatGeekApi: SeatGeekApi) {

    suspend fun getActiveEvents(searchQuery: String) =
            seatGeekApi.searchEvents(searchQuery, 20, BuildConfig.SEAT_GEEK_CLIENT_ID)

}
