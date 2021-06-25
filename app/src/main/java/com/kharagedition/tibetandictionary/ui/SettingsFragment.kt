package com.kharagedition.tibetandictionary.ui

import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.widget.EditText
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import com.kharagedition.tibetandictionary.R


class SettingsFragment : PreferenceFragmentCompat() {


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val preference: EditTextPreference? = findPreference("signature")
        preference?.setOnBindEditTextListener { editText ->
            editText.selectAll() // select all text
            editText.isSingleLine=true
            val maxLength = 10
            editText.filters = arrayOf<InputFilter>(LengthFilter(maxLength)) // set maxLength to 10
        }
    }

}