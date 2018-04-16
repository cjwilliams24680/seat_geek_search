package com.cjwilliams24680.seatgeeksearch.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cjwilliams24680.seatgeeksearch.databinding.SearchDetailFragmentBinding
import com.cjwilliams24680.seatgeeksearch.ui.common.BaseFragment
import com.cjwilliams24680.seatgeeksearch.ui.common.BaseFragmentCallback
import com.cjwilliams24680.seatgeeksearch.ui.screens.search.SearchFragment
import java.lang.ref.WeakReference

/**
 * Created by chris on 4/15/18.
 */

class SearchDetailFragment : BaseFragment() {

    private var binding: SearchDetailFragmentBinding? = null
    private var callback: WeakReference<BaseFragmentCallback>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callback!!.get()!!.getActivityComponent().inject(this)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = SearchDetailFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    fun setCallback(callback: SearchFragment.Callback) {
        this.callback = WeakReference(callback)
    }

}
