package com.kharagedition.englishtibetandictionary.provider

import android.content.SearchRecentSuggestionsProvider


/**
 * Created by kharag on 18,June,2021
 */
class MySuggestionProvider : SearchRecentSuggestionsProvider() {
    companion object {
        const val AUTHORITY = "com.example.MySuggestionProvider"
        const val MODE = DATABASE_MODE_QUERIES
    }

    init {
        setupSuggestions(AUTHORITY, MODE)
    }
}
