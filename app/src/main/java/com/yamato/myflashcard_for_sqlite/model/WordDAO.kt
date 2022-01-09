package com.yamato.myflashcard_for_sqlite.model

import android.media.MediaCodec
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.intellij.lang.annotations.JdkConstants

@Dao
interface WordDAO {
    @Query("select * from Word where created < :startCreated order by created desc limit :limit")
    // KotlinコルーチンのFlow<T>にすることでDBに更新があると、新しい結果が流れてくる
    fun getWithCreated(startCreated:Long,limit:Int): Flow<List<Word>>

    // 全レコードを取得する
    @Query("select * from Word")
    fun getAll(): Flow<List<Word>>

    // 全レコードの行数を取得する
    @Query("select count(*) from Word")
    fun getCount(): Flow<Int>

    // 復習対象のレコードの行数を取得する
    @Query("select count(*) from Word where wrong > 0")
    fun getReviewCount(): Flow<Int>

    @Insert
    suspend fun create(word: Word)

    @Update
    suspend fun update(word: Word)

    @Delete
    suspend fun delete(word: Word)
}