package com.cjwilliams24680.seatgeeksearch.network.models

import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

val Event.isVisible: Boolean
    get() = ZonedDateTime.now().isBefore(visibleUntilUtc)

val Event.shortDateTime: String
    get() = DateTimeFormatter.ofPattern("MM/dd hh:mm a").format(this.dateTimeLocal)

val Event.longDateTime: String
    get() = DateTimeFormatter.ofPattern("EEE MMM dd, yyyy hh:mm a").format(dateTimeLocal)