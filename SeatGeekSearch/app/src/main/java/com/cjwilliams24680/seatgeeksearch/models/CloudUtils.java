package com.cjwilliams24680.seatgeeksearch.models;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chris on 4/19/18.
 */

public class CloudUtils {

    public static Single<List<Event>> filterExpiredEvents(List<Event> events) {
        return Flowable.fromIterable(events)
                .subscribeOn(Schedulers.io())
                .filter(Event::isVisible)
                .toList();
    }

}
