package com.yamato.myflashcard_for_sqlite.repository

import com.yamato.myflashcard_for_sqlite.model.Word
import kotlinx.coroutines.flow.Flow

interface WordRepository {
    // 非同期で処理したいのでsuspendとする
    suspend fun create(word:String,commentary: String)

    //全件取得のメソッド
    fun getAll(): Flow<List<Word>>
}