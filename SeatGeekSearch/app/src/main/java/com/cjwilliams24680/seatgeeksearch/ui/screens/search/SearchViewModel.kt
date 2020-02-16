package com.cjwilliams24680.seatgeeksearch.ui.screens.search

import androidx.lifecycle.ViewModel
import com.cjwilliams24680.seatgeeksearch.data.UserPreferences
import com.cjwilliams24680.seatgeeksearch.network.SeatGeekApi
import com.cjwilliams24680.seatgeeksearch.repositories.EventRepository
import javax.inject.Inject

class SearchViewModel @Inject constructor(
        private val eventRepository: EventRepository,
        private val userPreferences: UserPreferences) : ViewModel() {



}
