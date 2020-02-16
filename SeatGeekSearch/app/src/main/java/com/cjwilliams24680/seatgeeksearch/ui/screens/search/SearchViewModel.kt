package com.cjwilliams24680.seatgeeksearch.ui.screens.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cjwilliams24680.seatgeeksearch.R
import com.cjwilliams24680.seatgeeksearch.data.UserPreferences
import com.cjwilliams24680.seatgeeksearch.network.models.shortDateTime
import com.cjwilliams24680.seatgeeksearch.repositories.EventRepository
import com.cjwilliams24680.seatgeeksearch.repositories.ResourceRepository
import com.cjwilliams24680.seatgeeksearch.ui.LiveDataEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class SearchViewModel @Inject constructor(
        private val eventRepository: EventRepository,
        private val userPreferences: UserPreferences,
        private val resourceRepository: ResourceRepository) : ViewModel() {

    private var lastQuery: String = userPreferences.getLastQuery()

    private val _events = MutableLiveData<List<SearchResultUIModel>>()
    val events: LiveData<List<SearchResultUIModel>>
        get() = _events

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _error = MutableLiveData<LiveDataEvent<Throwable>>()
    val error: LiveData<LiveDataEvent<Throwable>>
        get() = _error

    private val _showEmptyState = MutableLiveData<Boolean>()
    val showEmptyState: LiveData<Boolean>
        get() = _showEmptyState

    fun search(query: String = lastQuery) {
        if (query != lastQuery) {
            lastQuery = query
            userPreferences.setLastQuery(query)
        }
        _isLoading.value = true

        viewModelScope.launch(Dispatchers.Default) {
            val response = eventRepository.getActiveEvents(query)
            if (!response.isSuccessful) {
                _error.postValue(LiveDataEvent(Throwable(resourceRepository.getString(R.string.an_error_occurred))))
                _isLoading.postValue(false)
            } else {
                val uiModels = response.body()?.events.orEmpty().map { event ->
                    val priceText = if (event.stats.listingCount > 0) {
                        resourceRepository.getString(R.string.x_listings_from_y_dollars, event.stats.listingCount, event.stats.lowestPrice)
                    } else ""

                    SearchResultUIModel(
                            event.title,
                            event.venue.displayLocation,
                            priceText,
                            event.shortDateTime,
                            event.performerList.firstOrNull()?.image,
                            event
                    )
                }

                _events.postValue(uiModels)
                _isLoading.postValue(false)
                _showEmptyState.postValue(uiModels.isEmpty())
            }
        }
    }

}
