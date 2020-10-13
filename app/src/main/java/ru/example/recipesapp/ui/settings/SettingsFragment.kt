package ru.example.recipesapp.ui.settings

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import ru.example.recipesapp.R

class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        bindPreferenceSummaryToValue(findPreference(getString(R.string.ui_mode)))
    }

    private fun bindPreferenceSummaryToValue(preference: Preference?) {
        preference!!.onPreferenceChangeListener = this
        onPreferenceChange(
            preference,
            PreferenceManager
                .getDefaultSharedPreferences(preference.context)
                .getBoolean(preference.key, false)
        )
    }


    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        val stringValue = newValue.toString()
        Log.d("TAG", "onPreferenceChange: $stringValue")
        if (preference is SwitchPreferenceCompat)
            preference.summary = stringValue
        if (newValue as Boolean)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        return true
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        return when(preference.key) {
            getString(R.string.ui_mode) -> true
            else -> super.onPreferenceTreeClick(preference)
        }
    }
}