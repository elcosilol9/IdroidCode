package com.ardev.idroid.ui.settings.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import com.google.android.material.color.DynamicColors
import com.ardev.idroid.R
import com.ardev.idroid.ui.settings.*
import com.ardev.idroid.ui.settings.preference.IntListPreference
import com.ardev.idroid.ui.settings.preference.showIntListPreferenceDialog

class AppearanceSettingsFragment :
    BasePreferenceFragment(R.string.appearance),
    SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.appearance_preference)
        findPreference<Preference>(DYNAMIC_THEME)?.isVisible = DynamicColors.isDynamicColorAvailable()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe(this)
    }

    override fun onDestroyView() {
        unsubscribe(this)
        super.onDestroyView()
    }

    override fun onDisplayPreferenceDialog(preference: Preference) {
        when (preference) {
            is IntListPreference -> {
                showIntListPreferenceDialog(preference)
            }
            else -> super.onDisplayPreferenceDialog(preference)
        }
    }

    override fun onSharedPreferenceChanged(prefs: SharedPreferences?, key: String?) {
        when (key) {
            THEME -> AppCompatDelegate.setDefaultNightMode(theme)
           	DYNAMIC_THEME -> {
                showSnackbar("Restart the application to apply the dynamic colors")
            }
        }
    }
}
