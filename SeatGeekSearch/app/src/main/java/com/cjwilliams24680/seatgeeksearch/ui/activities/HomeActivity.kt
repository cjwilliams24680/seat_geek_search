package com.cjwilliams24680.seatgeeksearch.ui.activities

import android.app.FragmentManager
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import com.cjwilliams24680.seatgeeksearch.R
import com.cjwilliams24680.seatgeeksearch.dagger.ActivityComponent
import com.cjwilliams24680.seatgeeksearch.dagger.ActivityModule
import com.cjwilliams24680.seatgeeksearch.dagger.DaggerManager
import com.cjwilliams24680.seatgeeksearch.databinding.HomeActivityBinding
import com.cjwilliams24680.seatgeeksearch.models.Event
import com.cjwilliams24680.seatgeeksearch.ui.screens.SearchDetailFragment
import com.cjwilliams24680.seatgeeksearch.ui.screens.search.SearchFragment

class HomeActivity : AppCompatActivity(), SearchFragment.Callback, FragmentManager.OnBackStackChangedListener {

    private lateinit var binding: HomeActivityBinding
    private val activityComponent: ActivityComponent = DaggerManager.getApplicationComponent().plus(ActivityModule(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerManager.getApplicationComponent().inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.home_activity)
        val searchFragment = SearchFragment()
        searchFragment.setCallback(this)
        fragmentManager.beginTransaction().add(R.id.container, searchFragment).commit()
        fragmentManager.addOnBackStackChangedListener(this)
    }

    override fun onSearchItemSelected(event: Event) {
        val searchDetailFragment = SearchDetailFragment.create(event)
        searchDetailFragment.setCallback(this)

        fragmentManager.beginTransaction()
                .replace(R.id.container, searchDetailFragment)
                .addToBackStack("SearchDetailFragment")
                .commit()
    }

    override fun getActivityComponent(): ActivityComponent {
        return activityComponent
    }

    override fun showSnackbar(@StringRes text: Int, duration: Int) {
        Snackbar.make(binding.root, text, duration).show()
    }

    override fun onBackStackChanged() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(fragmentManager.backStackEntryCount > 0)
    }

    override fun onSupportNavigateUp(): Boolean {
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
            return true
        }

        return super.onSupportNavigateUp()
    }

}
