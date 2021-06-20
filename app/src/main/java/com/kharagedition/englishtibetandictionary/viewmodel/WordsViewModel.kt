package com.kharagedition.englishtibetandictionary.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.kharagedition.englishtibetandictionary.dao.WordDao
import com.kharagedition.englishtibetandictionary.model.Word
import com.kharagedition.englishtibetandictionary.util.WordsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by kharag on 17,June,2021
 */
@HiltViewModel
class WordsViewModel @Inject constructor(private var wordDao: WordDao, var context: Context) : ViewModel() {
    var searchList: LiveData<List<Word>> =MutableLiveData();
    var wordOfDay : LiveData<Word> =MutableLiveData()
    val wordsList = Pager(PagingConfig(10)) { WordsPagingSource(wordDao) }
        .flow
        .cachedIn(viewModelScope)

    // filter list of data when query submitted
    fun filterData(query: String) {
        searchList =   wordDao.getWordsFromDictionaryByQuery(query).asLiveData()
    }
    //UPDATE WORDS
    fun updateCurrentWord(word: Word){
        viewModelScope.launch {
            wordDao.update(word)
            Log.e("TAG", "updateCurrentPrayer: Updated", )
        }
    }
    fun generateWordOfTheDay(){
        Log.e("TAG", "generateWordOfTheDay: ", )
        wordOfDay =wordDao.getRandomWord().asLiveData()
    }

}