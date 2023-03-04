package com.sijan.dictionaryapp.feature_dictionary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sijan.dictionaryapp.feature_dictionary.domain.model.Meaning
import com.sijan.dictionaryapp.feature_dictionary.domain.model.WordInfo

@Entity
data class WordInfoEntity(
    val word:String,
    val phonetic: String,
    val origin:String,
    val meanings: List<Meaning>,
    @PrimaryKey
    val id: Int?= null
) {
    fun toWordInfo(): WordInfo {
        return WordInfo(
            word= word,
            phonetic = phonetic,
            origin = origin,
            meanings = meanings
        )

    }
}