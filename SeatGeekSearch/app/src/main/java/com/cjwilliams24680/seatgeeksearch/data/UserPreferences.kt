package com.cjwilliams24680.seatgeeksearch.data

import android.content.Context
import android.support.annotation.VisibleForTesting
import java.util.HashSet

/**
 * Created by chris on 4/19/18.
 */
class UserPreferences(context: Context) {

    private val FAVORITED_EVENTS_KEY = "FAVORITED_EVENTS_KEY"
    private val LAST_SEARCH_QUERY = "LAST_SEARCH_QUERY"
    private val sharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
    private var favoritedEvents = sharedPreferences.getStringSet(FAVORITED_EVENTS_KEY, HashSet<String>())

    fun toggleFavorite(eventId: Long) {
        if (isFavorite(eventId)) {
            removeFavorite(eventId)
        } else {
            addFavorite(eventId)
        }
    }

    private fun addFavorite(eventId: Long) {
        val stringVersion = eventId.toString()
        favoritedEvents.add(stringVersion)
        sharedPreferences.edit().putStringSet(FAVORITED_EVENTS_KEY, favoritedEvents).apply()
    }

    fun isFavorite(eventId: Long): Boolean {
        return favoritedEvents.contains(eventId.toString())
    }

    private fun removeFavorite(eventId: Long) {
        val stringVersion = eventId.toString()
        favoritedEvents.remove(stringVersion)
        sharedPreferences.edit().putStringSet(FAVORITED_EVENTS_KEY, favoritedEvents).apply()
    }

    fun setLastQuery(searchQuery: String) {
        sharedPreferences.edit().putString(LAST_SEARCH_QUERY, searchQuery).apply()
    }

    fun getLastQuery(): String {
        return sharedPreferences.getString(LAST_SEARCH_QUERY, "")
    }

    @VisibleForTesting fun clearPreferences() {
        sharedPreferences.edit().clear().apply()
    }

    @VisibleForTesting fun clearCache() {
        favoritedEvents = sharedPreferences.getStringSet(FAVORITED_EVENTS_KEY, HashSet<String>())
    }

}
