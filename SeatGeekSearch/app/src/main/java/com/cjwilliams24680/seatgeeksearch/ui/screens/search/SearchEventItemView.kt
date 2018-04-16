package com.cjwilliams24680.seatgeeksearch.ui.screens.search

import android.support.v7.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
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

        // Keep original size so that it we don't need to reload when going to search detail
        Glide.with(binding.root)
                .load(event.performerList[0].images.huge)
                .apply(RequestOptions.overrideOf(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL))
                .transition(DrawableTransitionOptions.withCrossFade())
                .thumbnail(0.1f)
                .into(binding.venueIamge)
    }
}
