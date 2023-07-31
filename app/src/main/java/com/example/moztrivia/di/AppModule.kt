package com.example.moztrivia.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moztrivia.data.playerData.PlayerDatabase
import com.example.moztrivia.data.playerData.PlayerDatabaseDao
import com.example.moztrivia.network.QuestionAPI
import com.example.moztrivia.repository.QuestionRepository
import com.example.moztrivia.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule  {
    @Singleton
    @Provides
    fun questionRepository(api:QuestionAPI)
    = QuestionRepository(api)

    @Singleton
    @Provides
    fun provideQuestionAPI(): QuestionAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuestionAPI::class.java)
    }

    @Singleton
    @Provides
    fun providePlayerDatabaseDao(playerDb:PlayerDatabase):PlayerDatabaseDao{
        return playerDb.PlayerDao()
    }
    @Singleton
    @Provides
    fun providePlayerDatabase(@ApplicationContext context: Context):PlayerDatabase
    = Room.databaseBuilder(
        context=context,
        PlayerDatabase::class.java,
        name="player_db"
    ).fallbackToDestructiveMigration().build()
}