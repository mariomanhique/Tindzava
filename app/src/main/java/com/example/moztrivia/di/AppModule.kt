package com.example.moztrivia.di

import android.content.Context
import androidx.room.Room
import com.example.moztrivia.data.playerData.PlayerDatabase
import com.example.moztrivia.data.playerData.PlayerDatabaseDao
import com.example.moztrivia.network.QuestionAPI
import com.example.moztrivia.repositories.firebaseRepository.AuthRepository
import com.example.moztrivia.repositories.firebaseRepository.AuthRepositoryImpl
import com.example.moztrivia.repositories.repository.QuestionRepository
import com.example.moztrivia.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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


    @Singleton
    @Provides
    fun provideFirebaseDatabase():DatabaseReference
    =FirebaseDatabase.getInstance().reference

    @Singleton
    @Provides
    fun provideFirebaseAuth():FirebaseAuth
    =FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideAuthRepositoryImpl(auth: AuthRepositoryImpl):AuthRepository{
        return auth
    }

}