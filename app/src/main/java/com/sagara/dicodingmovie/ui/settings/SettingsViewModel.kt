package com.sagara.dicodingmovie.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sagara.dicodingmovie.schedulers.WorkManagerLauncher
import com.sagara.dicodingmovie.utils.LocaleHelper
import com.sagara.dicodingmovie.utils.SharedPreferences

class SettingsViewModel(
    private val sharedPreferences: SharedPreferences,
    private val workManagerLauncher: WorkManagerLauncher
): ViewModel() {
    private val _dailyReminder = MutableLiveData<Boolean>()
    private val _releaseReminder = MutableLiveData<Boolean>()

    val dailyReminder: LiveData<Boolean>
        get() = _dailyReminder

    val releaseReminder: LiveData<Boolean>
        get() = _releaseReminder

    fun updateDaily(value: Boolean){
        sharedPreferences.isDailyReminder = value
        _dailyReminder.postValue(value)

        if (value) {
            workManagerLauncher.startDailyWork()
        }else{
            workManagerLauncher.stopDailyWork()
        }
    }

    fun updateRelease(value: Boolean){
        sharedPreferences.isRealeaseReminder = value
        _releaseReminder.postValue(value)

        if (value) {
            workManagerLauncher.startReleaseWork()
        }else{
            workManagerLauncher.stopReleaseWork()
        }
    }
}