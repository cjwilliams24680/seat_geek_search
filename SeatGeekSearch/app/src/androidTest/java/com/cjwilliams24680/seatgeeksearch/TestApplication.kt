package com.cjwilliams24680.seatgeeksearch

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

/**
 * Created by chris on 4/19/18.
 */
class TestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

}
