package com.kharagedition.englishtibetandictionary.dao

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kharagedition.englishtibetandictionary.di.ApplicationScope
import com.kharagedition.englishtibetandictionary.model.Word
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Word::class],version = 2,exportSchema = false)
abstract class WordDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao;
    class Callback @Inject constructor (private val database:Provider<WordDatabase>, var context: Context,
                                        @ApplicationScope private val applicationScope:CoroutineScope):RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            //val dao =database.get().prayerDao()
            Log.e("TAG", "onCreate: DATABASE CREATED", )

        }
    }
}