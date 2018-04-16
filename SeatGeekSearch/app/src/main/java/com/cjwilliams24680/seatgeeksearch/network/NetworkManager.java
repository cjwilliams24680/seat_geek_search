package com.cjwilliams24680.seatgeeksearch.network;

import android.util.Log;

import com.cjwilliams24680.seatgeeksearch.BuildConfig;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chris on 4/13/18.
 */

@Singleton
public class NetworkManager {

    private static final String TAG = SeatGeekApi.class.getSimpleName();

    private static int DEFAULT_MAX_RETRIES = 1;
    private static int DEFAULT_TIMEOUT_IN_SECONDS = 5;

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
                            .addCallAdapterFactory(
                                    RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                            .build()
                            .create(SeatGeekApi.class);
        }

        return seatGeekApi;
    }

    public OkHttpClient getClient() {
        if (client == null) {
            client = new OkHttpClient().newBuilder()
                    .addInterceptor(chain -> {
                        Request request = attachHeaders(chain.request());

                        int numTries = 0;
                        Response response = null;
                        boolean isTimeOutError = false;

                        while (numTries == 0 || (isTimeOutError && numTries <= DEFAULT_MAX_RETRIES)) {
                            ++numTries;
                            response = chain.withReadTimeout(DEFAULT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS).proceed(request);
                            final int code = response.code();
                            isTimeOutError = code == 502 || code == 503 || code == 504;

                            if (BuildConfig.DEBUG) {
                                String log = String.format("Network request:%s %s\nresult: %s\nattempt: %d", request.method(), request.url(), response.toString(), numTries);
                                if (response.isSuccessful()) {
                                    Log.d(TAG, log);
                                } else {
                                    Log.e(TAG, log);
                                }
                            }
                        }

                        return response;
                    })
                    .build();
        }

        return client;
    }

    private Request attachHeaders(Request original) {
        Request.Builder builder =
                original.newBuilder()
                        .addHeader("User-Agent", String.format("Basic %s", BuildConfig.SEAT_GEEK_CLIENT_ID));

        return builder.build();
    }

}
