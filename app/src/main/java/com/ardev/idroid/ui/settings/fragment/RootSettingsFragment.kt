package com.ardev.idroid.ui.settings.fragment

import android.os.Bundle
import com.ardev.idroid.R

class RootSettingsFragment : BasePreferenceFragment(R.string.settings) {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.root_preference)
    }
}
