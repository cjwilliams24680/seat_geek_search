package com.cjwilliams24680.seatgeeksearch.ui.screens.search

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.widget.SearchView
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.cjwilliams24680.seatgeeksearch.R
import com.cjwilliams24680.seatgeeksearch.databinding.SearchFragmentBinding
import com.cjwilliams24680.seatgeeksearch.di.DaggerManager
import com.cjwilliams24680.seatgeeksearch.network.models.Event
import com.cjwilliams24680.seatgeeksearch.ui.common.BaseFragment
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

    interface Callback {
        fun onSearchItemSelected(event: Event)
    }

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var searchViewModel: SearchViewModel

    private lateinit var binding: SearchFragmentBinding
    private val adapter: SearchAdapter = SearchAdapter(this)
    private var callback: WeakReference<Callback>? = null

    private val textChangeBuffer: PublishProcessor<String> = PublishProcessor.create()

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        searchViewModel.search()

        searchViewModel.error.observe(viewLifecycleOwner, Observer {
            it.pop()?.let {
                showError()
            }
        })

        searchViewModel.events.observe(viewLifecycleOwner, Observer { events ->
            adapter.events = events
            adapter.notifyDataSetChanged()
        })

        searchViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            showLoadingSpinner(it)
        })

        searchViewModel.showEmptyState.observe(viewLifecycleOwner, Observer {
            // todo
        })
    }

    private fun showError() {
        Snackbar.make(binding.root, R.string.an_error_occurred, Snackbar.LENGTH_SHORT).show()
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
                                { query -> searchViewModel.search(query) },
                                { showError() }
                        ))
    }

    fun setCallback(callback: Callback) {
        this.callback = WeakReference(callback)
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
        // todo can use navigation component instead of doing this
        callback!!.get()!!.onSearchItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        // takes focus away from search bar
        // i believe this should close the keyboard and remove the cursor but i'll need to test
        binding.eventsList.requestFocus()
        hideKeyboard()
        textChangeBuffer.onNext(query ?: "")
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        textChangeBuffer.onNext(newText ?: "")
        return true
    }
}