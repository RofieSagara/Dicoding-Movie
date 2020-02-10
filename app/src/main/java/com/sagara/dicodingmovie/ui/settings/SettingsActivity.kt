package com.sagara.dicodingmovie.ui.settings

import android.os.Bundle
import android.view.MenuItem
import com.sagara.dicodingmovie.R
import com.sagara.dicodingmovie.base.BaseActivity
import kotlinx.android.synthetic.main.activity_settings.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsActivity : BaseActivity() {
    private val viewModel: SettingsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)

        setSupportActionBar(settings_toolbar)

        settings_toolbar.title = applicationContext.getString(R.string.settings)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        supportFragmentManager.beginTransaction()
            .replace(R.id.settings_frame,
                SettingsPreferences()
            )
            .commit()
    }

    override fun onNavigateUp(): Boolean {
        onBackPressed()
        return super.onNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
