package com.cjwilliams24680.seatgeeksearch.ui.screens.searchDetail

import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.cjwilliams24680.seatgeeksearch.databinding.SearchDetailFragmentBinding
import com.cjwilliams24680.seatgeeksearch.network.models.Event
import com.cjwilliams24680.seatgeeksearch.ui.common.BaseFragment
import android.content.Intent
import android.net.Uri
import androidx.core.content.res.ResourcesCompat
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.cjwilliams24680.seatgeeksearch.R
import com.cjwilliams24680.seatgeeksearch.data.UserPreferences
import com.cjwilliams24680.seatgeeksearch.di.DaggerManager
import javax.inject.Inject


/**
 * Created by chris on 4/15/18.
 */

class SearchDetailFragment : BaseFragment() {

    companion object {
        private val EVENT_KEY = "SearchDetailFragment.EVENT_KEY"

        fun create(event: Event): SearchDetailFragment {
            val fragment = SearchDetailFragment()
            val extras = Bundle()
            extras.putParcelable(EVENT_KEY, event)
            fragment.arguments = extras
            return fragment
        }
    }

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var userPreferences: UserPreferences

    private lateinit var searchDetailViewModel: SearchDetailViewModel
    private lateinit var binding: SearchDetailFragmentBinding
    private var favoriteIcon: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerManager.getApplicationComponent().inject(this)
        searchDetailViewModel = ViewModelProviders.of(this, viewModelFactory)[SearchDetailViewModel::class.java]
        searchDetailViewModel.init(arguments!!.getParcelable(EVENT_KEY)!!)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = SearchDetailFragmentBinding.inflate(inflater, container, false)
        binding.vm = searchDetailViewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        searchDetailViewModel.data.observe(viewLifecycleOwner, Observer { uiModel ->
            // Keep original size so that it we don't need to reload when coming from search
            // Also dont use transition or thumbnail, since it's already loaded
            Glide.with(binding.root)
                    .load(uiModel.imageUrl)
                    .apply(RequestOptions.overrideOf(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL))
                    .into(binding.headerImage)
        })

        searchDetailViewModel.isFavorited.observe(viewLifecycleOwner, Observer {
            updateFavoriteIcon(it)
        })

        searchDetailViewModel.launchUrl.observe(viewLifecycleOwner, Observer {
            it.pop()?.run { onViewSeatGeek(this) }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.search_detail_menu, menu)
        favoriteIcon = menu.findItem(R.id.action_favorite)!!
        searchDetailViewModel.isFavorited.value?.let { updateFavoriteIcon(it) }
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        val icon = if (isFavorite) R.drawable.ic_favorite_white_24dp else R.drawable.ic_favorite_border_white_24dp
        favoriteIcon?.icon = ResourcesCompat.getDrawable(resources, icon, null)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite) {
            searchDetailViewModel.onFavoriteToggled()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun onViewSeatGeek(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

}
