package com.cjwilliams24680.seatgeeksearch.network;

import com.cjwilliams24680.seatgeeksearch.models.EventSearchResponse;

import io.reactivex.Flowable;
import retrofit2.Response
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by chris on 4/13/18.
 */

interface SeatGeekApi {

    @GET("events")
    fun searchEventsFlowable(
            @Query("q") searchQuery: String,
            @Query("per_page") itemsPerPage: Int,
            @Query("client_id") clientId: String) : Flowable<EventSearchResponse>

    @GET("events")
    suspend fun searchEvents(
            @Query("q") searchQuery: String,
            @Query("per_page") itemsPerPage: Int,
            @Query("client_id") clientId: String) : Response<EventSearchResponse>

}
