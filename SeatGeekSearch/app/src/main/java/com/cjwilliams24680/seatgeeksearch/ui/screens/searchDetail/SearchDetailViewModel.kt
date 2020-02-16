package com.cjwilliams24680.seatgeeksearch.ui.screens.searchDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cjwilliams24680.seatgeeksearch.R
import com.cjwilliams24680.seatgeeksearch.data.UserPreferences
import com.cjwilliams24680.seatgeeksearch.network.models.Event
import com.cjwilliams24680.seatgeeksearch.network.models.longDateTime
import com.cjwilliams24680.seatgeeksearch.repositories.ResourceRepository
import com.cjwilliams24680.seatgeeksearch.ui.LiveDataEvent
import javax.inject.Inject

class SearchDetailViewModel @Inject constructor(
        private val userPreferences: UserPreferences,
        private val resourceRepository: ResourceRepository) : ViewModel() {

    private lateinit var event: Event

    private val _isFavorited = MutableLiveData<Boolean>()
    val isFavorited : LiveData<Boolean>
        get() = _isFavorited

    private val _data = MutableLiveData<SearchDetailUIModel>()
    val data : MutableLiveData<SearchDetailUIModel>
        get() = _data

    private val _launchUrl = MutableLiveData<LiveDataEvent<String>>()
    val launchUrl : LiveData<LiveDataEvent<String>>
        get() = _launchUrl

    fun init(event: Event) {
        this.event = event
        val location = resourceRepository.getString(R.string.x_in_y,
                event.venue.name ?: "" ,
                event.venue.displayLocation ?: "")
        _data.value = SearchDetailUIModel(
                event.url,
                event.performerList.firstOrNull()?.image,
                event.title,
                event.longDateTime,
                location)


        _isFavorited.value = userPreferences.isFavorite(event.id)
    }

    fun onFavoriteToggled() {
        userPreferences.toggleFavorite(event.id)
        _isFavorited.value = _isFavorited.value != true
    }

    fun onLaunchClicked() {
        _launchUrl.value = LiveDataEvent(event.url)
    }

}
