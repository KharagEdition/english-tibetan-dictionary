package com.kharagedition.englishtibetandictionary.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.kharagedition.englishtibetandictionary.dao.WordDao
import com.kharagedition.englishtibetandictionary.util.WordsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by kharag on 17,June,2021
 */
@HiltViewModel
class WordsViewModel @Inject constructor(private var wordDao: WordDao, var context: Context) : ViewModel() {
    val prayers = wordDao.getWords().asLiveData()
    val prayersList = Pager(PagingConfig(10)) { WordsPagingSource(wordDao) }
        .flow
        .cachedIn(viewModelScope)
}