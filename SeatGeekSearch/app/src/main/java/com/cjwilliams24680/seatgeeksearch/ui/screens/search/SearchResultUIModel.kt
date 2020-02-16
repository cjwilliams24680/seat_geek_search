package com.cjwilliams24680.seatgeeksearch.ui.screens.search

import com.cjwilliams24680.seatgeeksearch.network.models.Event

data class SearchResultUIModel(
        val title: String,
        val subtitle: String?,
        val body: String,
        val date: String,
        val imageUrl: String?,
        val event: Event)
