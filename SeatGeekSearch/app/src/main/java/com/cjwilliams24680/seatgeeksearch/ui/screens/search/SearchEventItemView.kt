package com.cjwilliams24680.seatgeeksearch.ui.screens.search

import android.support.v7.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cjwilliams24680.seatgeeksearch.databinding.SearchEventItemViewBinding
import com.cjwilliams24680.seatgeeksearch.models.Event
import com.cjwilliams24680.seatgeeksearch.ui.common.ListItemCallback
import java.lang.ref.WeakReference

/**
 * Created by chris on 4/15/18.
 */

class SearchEventItemView(private val binding: SearchEventItemViewBinding, eventItemCallback: ListItemCallback<Event>) : RecyclerView.ViewHolder(binding.root) {

    private var callback: WeakReference<ListItemCallback<Event>> = WeakReference(eventItemCallback)

    fun update(event: Event) {
        binding.event = event
        binding.root.setOnClickListener { _ -> callback.get()!!.onItemSelected(event) }

        Glide.with(binding.root).load(event.venue.url).into(binding.venueIamge)
    }
}
