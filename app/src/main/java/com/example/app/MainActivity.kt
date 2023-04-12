package com.example.app

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import com.example.app.databinding.ActivityMainBinding
import com.example.app.fragments.FragmentAdditionalInfo
import com.example.app.fragments.FragmentMain
import com.example.app.fragments.constract.*


class MainActivity : AppCompatActivity(), Navigator {
    private lateinit var binding: ActivityMainBinding
    private val currentFragment: Fragment
        get() = supportFragmentManager.findFragmentById(R.id.fragmentContainer)!!

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?,
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            updateUi()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        window.navigationBarColor = resources.getColor(R.color.black);
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, FragmentMain())
                .commit()
        }
        binding.include.backButton.setOnClickListener {
            goBack()
        }
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)
    }

    override fun goBack() {
        onBackPressedDispatcher.onBackPressed()
    }

    override fun showAdditionalInfoScreen(id: Int, newItem: Boolean) {
        launchFragment(FragmentAdditionalInfo.newInstance(id, newItem))
    }

    private fun launchFragment(fragment: Fragment) {
        Log.d("MyLog", "$fragment")
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun updateUi() {
        val fragment = currentFragment
        if (fragment is FragmentAdditionalInfo) {
            binding.include.backButton.visibility = View.VISIBLE
        } else {
            binding.include.backButton.visibility = View.GONE
        }
        if (fragment is HasCustomTitle) {
            binding.include.actionTitle.text = getString(fragment.getTitleRes())
        } else {
            binding.include.actionTitle.text = getString(R.string.home_title)
        }

        if (fragment is HasCustomAction) {
            createCustomToolbarAction(fragment.getCustomAction())
        } else {
            binding.include.toolbar.menu.clear()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
    }

    private fun createCustomToolbarAction(action: CustomAction) {
        binding.include.toolbar.menu.clear()

        val iconDrawable = DrawableCompat.wrap(ContextCompat.getDrawable(this, action.iconRes)!!)
        iconDrawable.setTint(Color.WHITE)

        val menuItem = binding.include.toolbar.menu.add(action.textRes)
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        menuItem.icon = iconDrawable
        menuItem.setOnMenuItemClickListener {
            action.onCustomAction.run()
            return@setOnMenuItemClickListener true
        }
    }


    companion object {
        @JvmStatic
        private val KEY_RESULT = "RESULT"
    }
}
