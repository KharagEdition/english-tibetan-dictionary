package com.kharagedition.englishtibetandictionary.util

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kharagedition.englishtibetandictionary.dao.WordDao
import com.kharagedition.englishtibetandictionary.model.Word

/**
 * Created by kharag on 18,June,2021
 */

class WordsPagingSource(private val wordDao: WordDao) : PagingSource<Int, Word>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Word> {
        val position = params.key ?: INITIAL_PAGE_INDEX
        val randomWords = wordDao.getAllWordsFromDictionary(params.loadSize)
        return LoadResult.Page(
            data = randomWords,
            prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
            nextKey = if (randomWords.isNullOrEmpty()) null else position + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Word>): Int? = null
}