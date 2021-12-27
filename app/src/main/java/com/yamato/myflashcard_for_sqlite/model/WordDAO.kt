package com.yamato.myflashcard_for_sqlite.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDAO {
    @Query("select * from Word where created < :startCreated order by created desc limit :limit")
    // KotlinコルーチンのFlow<T>にすることでDBに更新があると、新しい結果が流れてくる
    fun getWithCreated(startCreated:Long,limit:Int): Flow<List<Word>>

//    @Query("select * from Word order by created desc")
    @Query("select * from Word")
    fun getAll(): Flow<List<Word>>

    @Insert
    suspend fun create(word: Word)

    @Update
    suspend fun update(word: Word)

    @Delete
    suspend fun delete(word: Word)
}