package com.cjwilliams24680.seatgeeksearch.models

import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Created by chris on 4/19/18.
 */
object CloudUtils {
    fun filterExpiredEvents(events: List<Event>?): Single<List<Event>> {
        return Flowable.fromIterable(events)
                .subscribeOn(Schedulers.io())
                .filter { obj: Event -> obj.isVisible }
                .toList()
    }
}