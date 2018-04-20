package com.cjwilliams24680.seatgeeksearch.ui.screens.searchDetail

import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.cjwilliams24680.seatgeeksearch.databinding.SearchDetailFragmentBinding
import com.cjwilliams24680.seatgeeksearch.models.Event
import com.cjwilliams24680.seatgeeksearch.ui.common.BaseFragment
import com.cjwilliams24680.seatgeeksearch.ui.common.BaseFragmentCallback
import com.cjwilliams24680.seatgeeksearch.ui.screens.search.SearchFragment
import java.lang.ref.WeakReference
import android.content.Intent
import android.net.Uri
import android.support.v4.content.res.ResourcesCompat
import android.view.*
import com.cjwilliams24680.seatgeeksearch.R
import com.cjwilliams24680.seatgeeksearch.data.UserPreferences
import javax.inject.Inject


/**
 * Created by chris on 4/15/18.
 */

class SearchDetailFragment : BaseFragment(), SearchDetailBindingListener {

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

    @Inject lateinit var userPreferences: UserPreferences

    private lateinit var binding: SearchDetailFragmentBinding
    private var callback: WeakReference<BaseFragmentCallback>? = null
    private lateinit var event: Event
    private lateinit var favoriteIcon: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        event = arguments.getParcelable(EVENT_KEY)
        callback!!.get()!!.getActivityComponent().inject(this)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = SearchDetailFragmentBinding.inflate(inflater, container, false)
        binding.event = event
        binding.listener = this

        // Keep original size so that it we don't need to reload when coming from search
        // Also dont use transition or thumbnail, since it's already loaded
        Glide.with(binding.root)
                .load(event.performerList[0].image)
                .apply(RequestOptions.overrideOf(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL))
                .into(binding.headerImage)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.search_detail_menu, menu)
        favoriteIcon = menu?.findItem(R.id.action_favorite)!!
        updateFavoriteIcon()
    }

    private fun updateFavoriteIcon() {
        val icon = if (userPreferences.isFavorite(event.id)) R.drawable.ic_favorite_white_24dp else R.drawable.ic_favorite_border_white_24dp
        favoriteIcon.icon = ResourcesCompat.getDrawable(resources, icon, null)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite) {
            userPreferences.toggleFavorite(event.id)
            updateFavoriteIcon()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun setCallback(callback: SearchFragment.Callback) {
        this.callback = WeakReference(callback)
    }

    override fun onViewSeatGeek(view: View) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(event.url))
        startActivity(browserIntent)
    }

}
