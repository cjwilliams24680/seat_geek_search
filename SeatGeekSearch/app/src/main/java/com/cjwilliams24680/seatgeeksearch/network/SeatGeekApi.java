package com.cjwilliams24680.seatgeeksearch.network;

import com.cjwilliams24680.seatgeeksearch.models.EventSearchResponse;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by chris on 4/13/18.
 */

public interface SeatGeekApi {

    @GET("event")
    Flowable<EventSearchResponse> searchEvents(@Query("q") String searchQuery);

}
