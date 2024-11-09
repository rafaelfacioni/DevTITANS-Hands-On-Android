package com.example.plaintext.data.di

import android.content.Context
import androidx.room.Room
import com.example.plaintext.data.PlainTextDatabase
import com.example.plaintext.data.dao.PasswordDao
import com.example.plaintext.data.repository.LocalPasswordDBStore
import com.example.plaintext.data.repository.PasswordDBStore
import com.example.plaintext.ui.screens.hello.dbSimulator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

data class loginInfo(
    var login: String,
    var password: String
)

@Module
@InstallIn(SingletonComponent::class)
object DataDiModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PlainTextDatabase {
        return Room.databaseBuilder(
            context,
            PlainTextDatabase::class.java,
            name = "plaintext_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providePasswordDao(database: PlainTextDatabase): PasswordDao {
        return database.passwordDao()
    }

    @Provides
    @Singleton
    fun providePasswordDBStore(passwordDao: PasswordDao): PasswordDBStore {
        return LocalPasswordDBStore(passwordDao)
    }

    @Provides
    @Singleton
    fun provideLoginInfo(): loginInfo = loginInfo(
        login = "devtitans",
        password = "123"
    )


    @Provides
    @Singleton
    fun provideDBSimulator(): dbSimulator = dbSimulator()
}