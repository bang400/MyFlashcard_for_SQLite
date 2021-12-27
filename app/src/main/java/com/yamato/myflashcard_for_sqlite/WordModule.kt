package com.yamato.myflashcard_for_sqlite

import android.content.Context
import androidx.room.Room
import com.yamato.myflashcard_for_sqlite.model.WordDAO
import com.yamato.myflashcard_for_sqlite.model.WordDatabase
import com.yamato.myflashcard_for_sqlite.repository.WordRepository
import com.yamato.myflashcard_for_sqlite.repository.WordRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WordModule {

    // WordDatabaseの作り方をHiltに教える
    @Singleton
    @Provides
    fun provideWordDatabase(
        // Hiltがアプリケーションコンテキストを渡す
        @ApplicationContext context: Context
    ): WordDatabase {
        return Room.databaseBuilder(
            context,
            WordDatabase::class.java,
            "word.db"
        ).build()
    }

    // WordDAOの作り方をHiltに教える
    @Singleton //複数箇所から必要とされた場合、同じインスタンスがセットされるようになる
    @Provides
    fun provideWordDAO(db: WordDatabase): WordDAO {
        return db.wordDAO()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class WordRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindWordRepository(
        impl: WordRepositoryImpl
    ): WordRepository
}