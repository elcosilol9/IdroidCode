package com.ardev.idroid.ui.settings

import android.os.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.activity.OnBackPressedCallback
import com.ardev.idroid.databinding.ActivitySettingsBinding
import com.ardev.idroid.R 
import com.ardev.idroid.app.AppActivity
import com.ardev.idroid.ext.*

class SettingsActivity :
	AppActivity(), 
	PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {
	private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     	binding = ActivitySettingsBinding.inflate(layoutInflater).apply {
        setContentView(root)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appbar.liftOnScrollTargetViewId = androidx.preference.R.id.recycler_view
        }
         onBackPressedDispatcher.addCallback(this, OnBackPressed())
        
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.container, RootSettingsFragment())
            }
        } else {
            savedInstanceState.let {
                supportActionBar?.title = it.getCharSequence(TITLE_TAG)
            }
        }
    
    }
    
    
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putCharSequence(TITLE_TAG, supportActionBar?.title)
    }

    override fun onTitleChanged(title: CharSequence?, color: Int) {
        super.onTitleChanged(title, color)
        binding.collapsingToolbar.title = title
    }

    override fun onPreferenceStartFragment(
        caller: PreferenceFragmentCompat,
        pref: Preference
    ): Boolean {
        val fragment = supportFragmentManager.fragmentFactory.instantiate(classLoader, pref.fragment ?: return false)
        fragment.arguments = pref.extras
        openFragment(fragment)
        supportActionBar?.title = pref.title ?: getString(R.string.settings)
        return true
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.container, fragment)
            addToBackStack(null)
        }
    }
	class BackPressed : OnBackPressedCallback(
            true
        ) {
            override fun handleOnBackPressed() {
               val current = supportFragmentManager.fragments.last()
                if (current !is RootSettingsFragment) {
                    openFragment(RootSettingsFragment())
                    return
                }
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q &&
                    isTaskRoot &&
                    supportFragmentManager.backStackEntryCount == 0
                ) { 
                    finishAfterTransition()
                }
                finish()
            }
        }

    companion object {
        private const val TITLE_TAG = "settings_title"
    }
}
