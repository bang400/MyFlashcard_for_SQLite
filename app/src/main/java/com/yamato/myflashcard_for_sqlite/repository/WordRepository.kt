package com.yamato.myflashcard_for_sqlite.repository

import com.yamato.myflashcard_for_sqlite.model.Word
import kotlinx.coroutines.flow.Flow

interface WordRepository {
    // 非同期で処理したいのでsuspendとする
    suspend fun create(word:String,commentary: String)

    suspend fun updateJudgement(word:Word,right: Int, wrong: Int) :Word

    //全件取得のメソッド
    fun getAll(): Flow<List<Word>>
}