package com.sagara.dicodingmovie.ui.settings

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.lifecycle.Observer
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.sagara.dicodingmovie.R
import com.sagara.dicodingmovie.ui.settings.SettingsViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SettingsPreferences: PreferenceFragmentCompat() {

    private val viewModel: SettingsViewModel by sharedViewModel()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey)

        val releaseReminder = findPreference<SwitchPreferenceCompat>("pref_release_reminder")
        val dailyReminder = findPreference<SwitchPreferenceCompat>("pref_daily_reminder")

        findPreference<Preference>("pref_change_language")?.setOnPreferenceClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            context?.startActivity(mIntent)

            return@setOnPreferenceClickListener true
        }

        viewModel.releaseReminder.observe(this, Observer {
            releaseReminder?.isChecked = it
        })

        viewModel.dailyReminder.observe(this, Observer {
            dailyReminder?.isChecked = it
        })

        releaseReminder?.setOnPreferenceClickListener { it as SwitchPreferenceCompat
            viewModel.updateRelease(it.isChecked)

            return@setOnPreferenceClickListener true
        }

        dailyReminder?.setOnPreferenceClickListener { it as SwitchPreferenceCompat
            viewModel.updateDaily(it.isChecked)

            return@setOnPreferenceClickListener true
        }
    }
}