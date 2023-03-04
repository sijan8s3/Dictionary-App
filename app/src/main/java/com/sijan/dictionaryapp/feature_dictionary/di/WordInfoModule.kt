package com.sijan.dictionaryapp.feature_dictionary.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.sijan.dictionaryapp.feature_dictionary.data.local.WordInfoDao
import com.sijan.dictionaryapp.feature_dictionary.data.local.WordInfoDatabase
import com.sijan.dictionaryapp.feature_dictionary.data.remote.DictionaryAppApi
import com.sijan.dictionaryapp.feature_dictionary.data.repository.WordInfoRepositoryImpl
import com.sijan.dictionaryapp.feature_dictionary.data.util.GsonParser
import com.sijan.dictionaryapp.feature_dictionary.domain.repository.WordInfoRepository
import com.sijan.dictionaryapp.feature_dictionary.domain.use_case.GetWordInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WordInfoModule {

    @Provides
    @Singleton
    fun provideGetWordInfoUseCase(repository: WordInfoRepository): GetWordInfo{
        return GetWordInfo(repository)
    }

    @Provides
    @Singleton
    fun providerWordInfoRepository(
        db: WordInfoDatabase,
        api: DictionaryAppApi
    ): WordInfoRepository{
        return WordInfoRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun provideWordInfoDatabase(app:Application): WordInfoDatabase{
        return Room.databaseBuilder(
            app, WordInfoDatabase::class.java, "dictionary_db"
        ).addTypeConverter(GsonParser(Gson())).build()
    }

    @Provides
    @Singleton
    fun provideDictionaryApi(): DictionaryAppApi{
        return Retrofit.Builder()
            .baseUrl(DictionaryAppApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryAppApi::class.java)
    }
}