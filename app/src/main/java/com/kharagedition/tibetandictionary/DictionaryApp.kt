package com.kharagedition.tibetandictionary

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import com.google.firebase.messaging.FirebaseMessaging
import com.kharagedition.tibetandictionary.util.Constant

/**
 * Created by kharag on 17,June,2021
 */
@HiltAndroidApp
class DictionaryApp : Application() {
    override fun onCreate() {

        Log.e("TAG", "onCreate: CREATED", )

        FirebaseMessaging.getInstance().subscribeToTopic(Constant.ALL)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.e("TAG", "onCreate: subscribeToTopic", )
                }else{
                    Log.e("TAG", "onCreate: subscribeToTopic failed", )
                }
            }
        super.onCreate()
    }
}