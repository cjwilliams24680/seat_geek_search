package com.cjwilliams24680.seatgeeksearch.ui.screens.searchDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
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

    private lateinit var binding: SearchDetailFragmentBinding
    private var callback: WeakReference<BaseFragmentCallback>? = null
    private lateinit var event: Event

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
        Glide.with(binding.root)
                .load(event.performerList[0].image)
                .apply(RequestOptions.overrideOf(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL))
                .into(binding.headerImage)

        return binding.root
    }

    fun setCallback(callback: SearchFragment.Callback) {
        this.callback = WeakReference(callback)
    }

    override fun onViewSeatGeek(view: View) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(event.url))
        startActivity(browserIntent)
    }

}
