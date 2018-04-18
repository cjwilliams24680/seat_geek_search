package com.cjwilliams24680.seatgeeksearch.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cjwilliams24680.seatgeeksearch.databinding.SearchDetailFragmentBinding
import com.cjwilliams24680.seatgeeksearch.models.Event
import com.cjwilliams24680.seatgeeksearch.ui.common.BaseFragment
import com.cjwilliams24680.seatgeeksearch.ui.common.BaseFragmentCallback
import com.cjwilliams24680.seatgeeksearch.ui.screens.search.SearchFragment
import java.lang.ref.WeakReference

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
        return binding.root
    }

    fun setCallback(callback: SearchFragment.Callback) {
        this.callback = WeakReference(callback)
    }

}
