package com.kharagedition.tibetandictionary.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.kharagedition.tibetandictionary.dao.WordDao
import com.kharagedition.tibetandictionary.model.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by kharag on 17,June,2021
 */
@HiltViewModel
class WordsViewModel @Inject constructor(private var wordDao: WordDao,  context: Context) : ViewModel() {
    var liveQuery: MutableLiveData<String> =MutableLiveData<String>()
    var wordOfDay : LiveData<Word> =MutableLiveData()
    //GET ALL WORDS
    val wordsList = Pager(PagingConfig(pageSize = 10,enablePlaceholders = false),pagingSourceFactory = {wordDao.getAllWordsFromDictionary()})
        .flow
        .cachedIn(viewModelScope)
            /*.map { pagingData ->
                pagingData.filter {it->
                     it.favourite==true
                }
            }*/
    //GET FAV WORDS
        val favWordsList = Pager(PagingConfig(pageSize = 10,enablePlaceholders = false),pagingSourceFactory = {wordDao.getFavWordsFromDictionary(true)})
                .flow
                .cachedIn(viewModelScope)
    //GET QUERY WORDS
    val queryWordsList = Pager(PagingConfig(pageSize = 10,enablePlaceholders = false),pagingSourceFactory = {wordDao.getQueryWordsFromDictionary(liveQuery.value)})
            .flow

    // filter list of data when query submitted
    fun filterData(query: String) {
        liveQuery.postValue(query)
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

    val words = wordDao.getWords().asLiveData()

}