package com.cjwilliams24680.seatgeeksearch.ui.screens.search

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.cjwilliams24680.seatgeeksearch.databinding.SearchEventItemViewBinding
import com.cjwilliams24680.seatgeeksearch.network.models.Event
import com.cjwilliams24680.seatgeeksearch.ui.common.ListItemCallback
import java.lang.ref.WeakReference

/**
 * Created by chris on 4/15/18.
 */

class SearchEventItemView(private val binding: SearchEventItemViewBinding, val callback: ListItemCallback<Event>) : RecyclerView.ViewHolder(binding.root) {

    fun update(uiModel: SearchResultUIModel) {
        binding.data = uiModel
        binding.callback = callback

        // Keep original size so that it we don't need to reload when going to search detail
        Glide.with(binding.root)
                .load(uiModel.imageUrl)
                .apply(RequestOptions.overrideOf(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL))
                .transition(DrawableTransitionOptions.withCrossFade())
                .thumbnail(0.1f)
                .into(binding.venueImage)
    }
}
