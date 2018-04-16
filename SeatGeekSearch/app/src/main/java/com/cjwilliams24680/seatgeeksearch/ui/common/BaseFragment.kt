package com.cjwilliams24680.seatgeeksearch.ui.common

import android.app.Fragment
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by chris on 4/15/18.
 */
abstract class BaseFragment : Fragment() {

    val disposables: CompositeDisposable = CompositeDisposable()

    override fun onStop() {
        // This will cancel outstanding asynchronous requests so they dont come back and crash the app
        disposables.clear()
        super.onStop()
    }
}
