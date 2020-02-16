package com.cjwilliams24680.seatgeeksearch.ui.screens.search

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.widget.SearchView
import android.util.Log
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.cjwilliams24680.seatgeeksearch.BuildConfig
import com.cjwilliams24680.seatgeeksearch.R
import com.cjwilliams24680.seatgeeksearch.data.UserPreferences
import com.cjwilliams24680.seatgeeksearch.databinding.SearchFragmentBinding
import com.cjwilliams24680.seatgeeksearch.di.DaggerManager
import com.cjwilliams24680.seatgeeksearch.models.CloudUtils
import com.cjwilliams24680.seatgeeksearch.models.Event
import com.cjwilliams24680.seatgeeksearch.network.SeatGeekApi
import com.cjwilliams24680.seatgeeksearch.repositories.EventRepository
import com.cjwilliams24680.seatgeeksearch.ui.common.BaseFragment
import com.cjwilliams24680.seatgeeksearch.ui.common.BaseFragmentCallback
import com.cjwilliams24680.seatgeeksearch.ui.common.ListItemCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by chris on 4/15/18.
 */

class SearchFragment : BaseFragment(), ListItemCallback<Event> , SearchView.OnQueryTextListener{

    companion object {
        val TAG = "SearchFragment"
    }

    interface Callback : BaseFragmentCallback {
        fun onSearchItemSelected(event: Event)
    }

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject lateinit var eventRepository: EventRepository
    @Inject lateinit var userPreferences: UserPreferences

    private lateinit var searchViewModel: SearchViewModel

    private lateinit var binding: SearchFragmentBinding
    private val adapter: SearchAdapter = SearchAdapter(this)
    private var callback: WeakReference<Callback>? = null

    private val textChangeBuffer: PublishProcessor<String> = PublishProcessor.create()
    private var lastQuery = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        DaggerManager.getApplicationComponent().inject(this)
        searchViewModel = ViewModelProviders.of(this, viewModelFactory)[SearchViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        binding.eventsList.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoadingSpinner(true)
        searchEvents(userPreferences.getLastQuery())
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
    }

    override fun onStart() {
        super.onStart()

        // This will allow us to update the search results as the user types but only when they pause typing
        disposables.add(
                textChangeBuffer.subscribeOn(Schedulers.io())
                        .debounce(300, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
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
        if (searchQuery.isBlank() && searchQuery == lastQuery) {
            showLoadingSpinner(false)
            return
        }

        disposables.add(
                eventRepository.getActiveEventsRx(searchQuery)
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
        textChangeBuffer.onNext(query ?: "")
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        showLoadingSpinner(false)
        textChangeBuffer.onNext(newText ?: "")
        return true
    }
}