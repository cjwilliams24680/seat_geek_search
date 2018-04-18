package com.cjwilliams24680.seatgeeksearch.ui.common

import android.app.Fragment
import android.content.Context
import android.view.inputmethod.InputMethodManager
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

    protected fun hideKeyboard() {
        val rootView = activity.currentFocus
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if (rootView != null && rootView.windowToken != null) {
            val result = inputMethodManager.hideSoftInputFromWindow(rootView.windowToken, 0)
            if (result) {
                return
            }
        }
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }
}
