package com.cjwilliams24680.seatgeeksearch.data

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by chris on 4/19/18.
 */
@RunWith(AndroidJUnit4::class)
class UserPreferencesTest {

    private var userPreferences: UserPreferences = UserPreferences(InstrumentationRegistry.getInstrumentation().targetContext)

    @Before
    fun setup() {
        userPreferences.clearPreferences()
        userPreferences.clearCache()
    }

    @Test
    fun lastQuery_test() {
        Assert.assertEquals("", userPreferences.getLastQuery())
        userPreferences.setLastQuery("hello")
        Assert.assertEquals("hello", userPreferences.getLastQuery())
        userPreferences.setLastQuery("world")
        Assert.assertEquals("world", userPreferences.getLastQuery())
    }

    @Test
    fun isFavorite_test() {
        Assert.assertFalse(userPreferences.isFavorite(1000))
        userPreferences.toggleFavorite(1000)
        Assert.assertTrue(userPreferences.isFavorite(1000))
        userPreferences.toggleFavorite(1000)
        Assert.assertFalse(userPreferences.isFavorite(1000))
        userPreferences.toggleFavorite(1000)
        userPreferences.toggleFavorite(5000)
        userPreferences.toggleFavorite(10)
        userPreferences.toggleFavorite(200)
        userPreferences.toggleFavorite(12345)
        Assert.assertTrue(userPreferences.isFavorite(200))
        userPreferences.clearCache()
        Assert.assertTrue(userPreferences.isFavorite(200))

    }
}