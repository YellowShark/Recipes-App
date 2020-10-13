package ru.example.recipesapp.ui

import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import ru.example.recipesapp.R

abstract class BaseFragment(layoutResource: Int) : Fragment(layoutResource) {
    override fun onResume() {
        super.onResume()

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val isChecked = sharedPref.getBoolean(getString(R.string.ui_mode), true)

        if (isChecked)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}