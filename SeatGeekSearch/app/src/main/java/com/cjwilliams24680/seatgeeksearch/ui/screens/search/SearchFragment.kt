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
import com.cjwilliams24680.seatgeeksearch.ui.common.ListItemCallback
import com.cjwilliams24680.seatgeeksearch.utils.TextUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

/**
 * Created by chris on 4/15/18.
 */

class SearchFragment : BaseFragment(), ListItemCallback<Event> , android.support.v7.widget.SearchView.OnQueryTextListener{

    companion object {
        val TAG = "SearchFragment"
    }

    interface Callback : BaseFragmentCallback {
        fun onSearchItemSelected(event: Event)
    }

    // todo need to figure out how to inject in Kotlin with dagger
    var seatGeekApi: SeatGeekApi? = null

    private var binding: SearchFragmentBinding? = null
    private val adapter: SearchAdapter = SearchAdapter(this)
    private var callback: WeakReference<Callback>? = null

    private val textChangeBuffer: BehaviorProcessor<String> = BehaviorProcessor.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callback!!.get()!!.getActivityComponent().inject(this)

        // todo make this the context of the last search?
        textChangeBuffer.onNext("")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        binding!!.eventsList.adapter = adapter
        binding!!.searchField.setOnQueryTextListener(this)
        return binding!!.root
    }

    override fun onStart() {
        super.onStart()

        // This will allow us to update the search results as the user types but only when they pause typing
        disposables.add(
                textChangeBuffer.subscribeOn(Schedulers.io())
                        .debounce(300, TimeUnit.MILLISECONDS)
                        .subscribe(
                                this::searchEvents,
                                {error -> Log.e(TAG, "An error occurred while processing search text", error)}))
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
                                {eventsResponse ->
                                    adapter.events = eventsResponse.events
                                    showLoadingSpinner(false)
                                },
                                {error ->
                                    Log.e(TAG, "There was an error while loading search results", error)
                                    showLoadingSpinner(false)
                                }))
    }

    private fun showLoadingSpinner(isVisible: Boolean) {
        TODO("Havent added a progress spinner to the layout yet")
    }

    override fun onItemSelected(item: Event) {
        callback!!.get()!!.onSearchItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        // takes focus away from search bar
        // i believe this should close the keyboard and remove the cursor but i'll need to test
        binding!!.eventsList.requestFocus()
        showLoadingSpinner(true)
        searchEvents(TextUtils.nonNullify(query))
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        showLoadingSpinner(false)
        searchEvents(TextUtils.nonNullify(newText))
        return true
    }
}