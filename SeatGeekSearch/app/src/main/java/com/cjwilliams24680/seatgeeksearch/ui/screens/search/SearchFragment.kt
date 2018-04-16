package com.cjwilliams24680.seatgeeksearch.ui.screens.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cjwilliams24680.seatgeeksearch.databinding.SearchFragmentBinding
import com.cjwilliams24680.seatgeeksearch.models.Event
import com.cjwilliams24680.seatgeeksearch.network.SeatGeekApi
import com.cjwilliams24680.seatgeeksearch.ui.common.BaseFragment
import com.cjwilliams24680.seatgeeksearch.ui.common.BaseFragmentCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import java.lang.ref.WeakReference
import javax.inject.Inject

/**
 * Created by chris on 4/15/18.
 */

class SearchFragment : BaseFragment() {

    companion object {
        public val TAG = "SearchFragment"
    }

    interface Callback : BaseFragmentCallback {
        fun onSearchItemSelected(event: Event)
    }

    @Inject var seatGeekApi: SeatGeekApi? = null

    private var binding: SearchFragmentBinding? = null
    private val adapter: SearchAdapter = SearchAdapter()
    private var callback: WeakReference<Callback>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callback!!.get()!!.getActivityComponent().inject(this)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        binding!!.eventsList.adapter = adapter
        return binding!!.root
    }

    fun setCallback(callback: Callback) {
        this.callback = WeakReference(callback)
    }

    /**
     * todo write a diff util so the items animate in rather than just flash/appear
     */
    private fun searchEvents(searchQuery: String) {
        disposables.add(
                seatGeekApi!!.searchEvents(searchQuery)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {eventsResponse -> adapter.setEvents(eventsResponse.events)},
                                {error -> Log.e(TAG, "There was an error while loading search results", error)}))
    }

    private fun showLoadingSpinner(isVisible: Boolean) {

    }
}