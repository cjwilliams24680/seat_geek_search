package com.cjwilliams24680.seatgeeksearch.ui.screens.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.cjwilliams24680.seatgeeksearch.databinding.SearchEventItemViewBinding
import com.cjwilliams24680.seatgeeksearch.models.Event
import com.cjwilliams24680.seatgeeksearch.ui.common.ListItemCallback
import java.lang.ref.WeakReference
import java.util.*

/**
 * Created by chris on 4/15/18.
 */

class SearchAdapter(eventItemCallback: ListItemCallback<Event>) : RecyclerView.Adapter<SearchEventItemView>(), ListItemCallback<Event> {

    private var callback: WeakReference<ListItemCallback<Event>> = WeakReference(eventItemCallback)
    var events: List<Event> = Collections.emptyList()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SearchEventItemView {
        return SearchEventItemView(SearchEventItemViewBinding.inflate(LayoutInflater.from(parent!!.context), parent, false), callback.get()!!)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: SearchEventItemView?, position: Int) {
        holder!!.update(events[position])
    }

    override fun onItemSelected(item: Event) {
        callback.get()!!.onItemSelected(item)
    }

}