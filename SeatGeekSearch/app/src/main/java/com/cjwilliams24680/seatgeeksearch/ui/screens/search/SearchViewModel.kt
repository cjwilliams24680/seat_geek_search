package com.cjwilliams24680.seatgeeksearch.ui.screens.search

import androidx.lifecycle.ViewModel
import com.cjwilliams24680.seatgeeksearch.data.UserPreferences
import com.cjwilliams24680.seatgeeksearch.network.SeatGeekApi
import javax.inject.Inject

class SearchViewModel @Inject constructor(
        private val seatGeekApi: SeatGeekApi,
        private val userPreferences: UserPreferences) : ViewModel() {



}
