package com.kharagedition.englishtibetandictionary.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.kharagedition.englishtibetandictionary.R

class SettingActivity : AppCompatActivity() {
    lateinit var materialToolbar: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        materialToolbar = findViewById(R.id.setting_backbtn)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.settings_container,
                    SettingsFragment()
                )
                .commit()
        }
        initListener();

    }
    private fun initListener() {
        materialToolbar.setOnClickListener{
            finish();
        }
    }

}