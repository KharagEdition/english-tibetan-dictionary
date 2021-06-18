package com.kharagedition.englishtibetandictionary.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kharagedition.englishtibetandictionary.R
import com.kharagedition.englishtibetandictionary.dao.WordDatabase
import com.kharagedition.englishtibetandictionary.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.bhudha)
            .error(R.drawable.bhudha)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
    )

    @Singleton
    @Provides
    fun provideDatabase(
        app: Application,
        callback: WordDatabase.Callback
    ) = Room
            .databaseBuilder(app, WordDatabase::class.java, Constant.DATABASE_NAME)
            .createFromAsset(Constant.DATABASE_DIR)
            .addCallback(callback)
            .build()

    @Provides
    fun provideTaskDao(db: WordDatabase) = db.wordDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context) = context

}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope