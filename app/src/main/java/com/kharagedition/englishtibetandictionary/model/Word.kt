package com.kharagedition.englishtibetandictionary.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * Created by kharag on 17,June,2021
 */
@Entity(tableName = "DICTIONARY")
@Parcelize
class Word(@PrimaryKey(autoGenerate = true)
           var id: Int? = null,
           var wylie:String? = null,
           var english:String? = null,
           var defination: String? = null,
           var favourite: Boolean? = false,) : Parcelable {

}