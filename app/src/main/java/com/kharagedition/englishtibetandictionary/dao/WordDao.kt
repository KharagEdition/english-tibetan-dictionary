package com.kharagedition.englishtibetandictionary.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.kharagedition.englishtibetandictionary.model.Word
/**
 * Created by kharag on 17,June,2021
 */
@Dao
interface WordDao {

    @Query("SELECT * FROM DICTIONARY LIMIT :size ")
    suspend fun getAllWordsFromDictionary(size: Int): List<Word>

    @Query("SELECT * FROM DICTIONARY WHERE english like :query ")
     fun getWordsFromDictionaryByQuery(query: String): Flow<List<Word>>


    @Query("SELECT * FROM DICTIONARY LIMIT 20")
    fun getWords(): Flow<List<Word>>

    @Query("SELECT * FROM DICTIONARY  WHERE english=:liked")
    fun getFavouritePrayer(liked: Boolean = true): Flow<List<Word>>

    @Query("SELECT * FROM DICTIONARY  WHERE id=:id")
    fun getPrayer(id: Int?): Flow<Word>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(prayer : Word)

    @Update
    suspend fun update(word: Word)

    @Query("SELECT * FROM DICTIONARY WHERE LENGTH(english)>7 ORDER BY RANDOM() LIMIT 1")
    fun getRandomWord(): Flow<Word>

}