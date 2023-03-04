package com.sijan.dictionaryapp.feature_dictionary.data.repository

import com.sijan.dictionaryapp.core.util.Resource
import com.sijan.dictionaryapp.feature_dictionary.data.local.WordInfoDao
import com.sijan.dictionaryapp.feature_dictionary.data.remote.DictionaryAppApi
import com.sijan.dictionaryapp.feature_dictionary.domain.model.WordInfo
import com.sijan.dictionaryapp.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class WordInfoRepositoryImpl(
    private var api: DictionaryAppApi,
    private val dao: WordInfoDao
):WordInfoRepository {
    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {
        emit(Resource.Loading())

        val wordInfo= dao.getWordInfo(word).map { it.toWordInfo() }
        emit(Resource.Loading(wordInfo))
        try {
            val remoteWordInfo= api.getWordInfo(word)
            dao.deleteWordInfo(remoteWordInfo.map { it.word })
            dao.insertWordInfo(remoteWordInfo.map { it.toWordInfoEntity() })
        }catch (e:HttpException){
            emit(Resource.Error(message = "Something went wrong!",
            data = wordInfo))
        }catch (e: IOException){
            emit(Resource.Error(message = "Couldn't reach server.",
                data = wordInfo))
        }

        val newWordInfo= dao.getWordInfo(word).map { it.toWordInfo() }
        emit(Resource.Success(newWordInfo))

    }
}