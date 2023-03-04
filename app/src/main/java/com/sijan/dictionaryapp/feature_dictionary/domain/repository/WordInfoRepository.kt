package com.sijan.dictionaryapp.feature_dictionary.domain.repository

import com.sijan.dictionaryapp.core.util.Resource
import com.sijan.dictionaryapp.feature_dictionary.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow

interface WordInfoRepository {
    fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>>
}