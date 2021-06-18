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
    abstract suspend fun getAllWordsFromDictionary(size: Int): List<Word>

    @Query("SELECT * FROM DICTIONARY LIMIT 20")
    fun getWords(): Flow<List<Word>>

    @Query("SELECT * FROM DICTIONARY  WHERE english=:liked")
    fun getFavouritePrayer(liked: Boolean = true): Flow<List<Word>>
    @Query("SELECT * FROM DICTIONARY  WHERE id=:id")
    fun getPrayer(id: Int?): Flow<Word>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(prayer : Word)
    @Update
    suspend fun update(prayer: Word)
    @Query("SELECT * FROM DICTIONARY  WHERE english=:title LIMIT 1")
    fun getPrayerByName(title: String?): Flow<Word>
}