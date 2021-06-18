package com.kharagedition.englishtibetandictionary.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kharagedition.englishtibetandictionary.dao.WordDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by kharag on 17,June,2021
 */
@HiltViewModel
class WordsViewModel @Inject constructor(private var wordDao: WordDao, var context: Context) : ViewModel() {
    val prayers = wordDao.getWords().asLiveData()
}