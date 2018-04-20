package com.cjwilliams24680.seatgeeksearch.models

import android.support.test.runner.AndroidJUnit4
import junit.framework.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

/**
 * Created by chris on 4/19/18.
 */
@RunWith(AndroidJUnit4::class)
class EventUtilsTest {

    @Test
    fun filterExpiredEvents_test() {
        val events: ArrayList<Event> = ArrayList()
        events.add(createEvent(0, ZonedDateTime.now(ZoneId.of("UTC")).minusDays(1)))
        events.add(createEvent(1, ZonedDateTime.now(ZoneId.of("UTC")).plusDays(1).minusYears(1)))
        events.add(createEvent(2, ZonedDateTime.now(ZoneId.of("UTC")).plusSeconds(30)))
        events.add(createEvent(3, ZonedDateTime.now(ZoneId.of("UTC")).minusDays(1).plusYears(1)))

        // Make sure expired events are gone, live events are still present, and that order has been maintained
        val filteredEvents = CloudUtils.filterExpiredEvents(events).blockingGet()
        Assert.assertEquals(2, filteredEvents.size)
        Assert.assertEquals(2, filteredEvents[0].id)
        Assert.assertEquals(3, filteredEvents[1].id)
    }

    private fun createEvent(eventId: Long, utcDateTime: ZonedDateTime): Event {
        val event = Event()
        event.id = eventId
        event.visibleUntilUtc = utcDateTime
        return event
    }

}
