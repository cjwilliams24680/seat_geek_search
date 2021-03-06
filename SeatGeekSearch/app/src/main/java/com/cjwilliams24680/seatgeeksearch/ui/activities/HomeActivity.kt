package com.cjwilliams24680.seatgeeksearch.ui.activities

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import com.cjwilliams24680.seatgeeksearch.R
import com.cjwilliams24680.seatgeeksearch.di.ActivityComponent
import com.cjwilliams24680.seatgeeksearch.di.ActivityModule
import com.cjwilliams24680.seatgeeksearch.di.DaggerManager
import com.cjwilliams24680.seatgeeksearch.databinding.HomeActivityBinding
import com.cjwilliams24680.seatgeeksearch.network.models.Event
import com.cjwilliams24680.seatgeeksearch.ui.screens.searchDetail.SearchDetailFragment
import com.cjwilliams24680.seatgeeksearch.ui.screens.search.SearchFragment

class HomeActivity : AppCompatActivity(), SearchFragment.Callback, FragmentManager.OnBackStackChangedListener {

    private lateinit var binding: HomeActivityBinding
    val activityComponent: ActivityComponent = DaggerManager.getApplicationComponent().plus(ActivityModule(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerManager.getApplicationComponent().inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.home_activity)
        val searchFragment = SearchFragment()
        searchFragment.setCallback(this)
        supportFragmentManager.beginTransaction().add(R.id.container, searchFragment).commit()
        supportFragmentManager.addOnBackStackChangedListener(this)
    }

    override fun onSearchItemSelected(event: Event) {
        val searchDetailFragment = SearchDetailFragment.create(event)

        supportFragmentManager.beginTransaction()
                .replace(R.id.container, searchDetailFragment)
                .addToBackStack("SearchDetailFragment")
                .commit()
    }

    override fun onBackStackChanged() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(supportFragmentManager.backStackEntryCount > 0)
    }

    override fun onSupportNavigateUp(): Boolean {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            return true
        }

        return super.onSupportNavigateUp()
    }

}
