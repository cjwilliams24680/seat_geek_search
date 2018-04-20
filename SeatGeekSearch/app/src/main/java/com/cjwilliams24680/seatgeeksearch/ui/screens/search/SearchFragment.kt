package com.cjwilliams24680.seatgeeksearch.ui.screens.search

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import com.cjwilliams24680.seatgeeksearch.BuildConfig
import com.cjwilliams24680.seatgeeksearch.R
import com.cjwilliams24680.seatgeeksearch.data.UserPreferences
import com.cjwilliams24680.seatgeeksearch.databinding.SearchFragmentBinding
import com.cjwilliams24680.seatgeeksearch.models.CloudUtils
import com.cjwilliams24680.seatgeeksearch.models.Event
import com.cjwilliams24680.seatgeeksearch.network.SeatGeekApi
import com.cjwilliams24680.seatgeeksearch.ui.common.BaseFragment
import com.cjwilliams24680.seatgeeksearch.ui.common.BaseFragmentCallback
import com.cjwilliams24680.seatgeeksearch.ui.common.ListItemCallback
import com.cjwilliams24680.seatgeeksearch.utils.TextUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit
import javax.inject.Inject

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

    @Inject lateinit var seatGeekApi: SeatGeekApi
    @Inject lateinit var userPreferences: UserPreferences

    private lateinit var binding: SearchFragmentBinding
    private val adapter: SearchAdapter = SearchAdapter(this)
    private var callback: WeakReference<Callback>? = null

    private val textChangeBuffer: PublishProcessor<String> = PublishProcessor.create()
    private var lastQuery = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        callback!!.get()!!.getActivityComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        binding.eventsList.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoadingSpinner(true)
        searchEvents(userPreferences.getLastQuery())
    }

    override fun onCreateOptionsMenu(menu: Menu?, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
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

    override fun onStop() {
        super.onStop()
        userPreferences.setLastQuery(lastQuery)
    }

    fun setCallback(callback: Callback) {
        this.callback = WeakReference(callback)
    }

    private fun searchEvents(searchQuery: String) {
        if (searchQuery == lastQuery) {
            showLoadingSpinner(false)
            return
        }

        disposables.add(
                seatGeekApi.searchEvents(searchQuery, 20, BuildConfig.SEAT_GEEK_CLIENT_ID)
                        .flatMapSingle { response -> CloudUtils.filterExpiredEvents(response.events) }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {events ->
                                    lastQuery = searchQuery
                                    adapter.events = events
                                    adapter.notifyDataSetChanged()
                                    showLoadingSpinner(false)
                                },
                                {error ->
                                    Log.e(TAG, "There was an error while loading search results", error)
                                    showLoadingSpinner(false)
                                    callback!!.get()!!.showSnackbar(R.string.an_error_occurred, Snackbar.LENGTH_SHORT)
                                }))
    }

    private fun showLoadingSpinner(isVisible: Boolean) {
        if (isVisible) {
            binding.progressSpinner.visibility = View.VISIBLE
            binding.progressSpinner.animate()
        } else {
            binding.progressSpinner.visibility = View.GONE
        }
    }

    override fun onItemSelected(item: Event) {
        callback!!.get()!!.onSearchItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        // takes focus away from search bar
        // i believe this should close the keyboard and remove the cursor but i'll need to test
        binding.eventsList.requestFocus()
        showLoadingSpinner(true)
        hideKeyboard()
        textChangeBuffer.onNext(TextUtils.nonNullify(query))
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        showLoadingSpinner(false)
        textChangeBuffer.onNext(TextUtils.nonNullify(newText))
        return true
    }
}