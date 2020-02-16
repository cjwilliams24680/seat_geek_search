package com.cjwilliams24680.seatgeeksearch.network;

import com.cjwilliams24680.seatgeeksearch.BuildConfig;
import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chris on 4/13/18.
 */

@Singleton
public class NetworkManager {

    private OkHttpClient client;
    private SeatGeekApi seatGeekApi;

    @Inject
    public NetworkManager() {
    }

    public SeatGeekApi getSeatGeekApi() {
        if (seatGeekApi == null) {
            seatGeekApi =
                    new Retrofit.Builder()
                            .baseUrl("https://api.seatgeek.com/2/")
                            .client(getClient())
                            .addConverterFactory(GsonConverterFactory.create(new Gson()))
                            .build()
                            .create(SeatGeekApi.class);
        }

        return seatGeekApi;
    }

    private OkHttpClient getClient() {
        if (client == null) {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
                builder.addInterceptor(logging);
            }

            client = builder.build();
        }

        return client;
    }

}
