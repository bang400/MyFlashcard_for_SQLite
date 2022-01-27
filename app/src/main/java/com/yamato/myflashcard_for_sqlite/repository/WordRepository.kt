package com.yamato.myflashcard_for_sqlite.repository

import com.yamato.myflashcard_for_sqlite.model.Word
import kotlinx.coroutines.flow.Flow

interface WordRepository {
    // 非同期で処理したいのでsuspendとする
    suspend fun create(word:String,commentary: String)

    // １件分のデータを削除
    suspend fun delete(word: Word)

    suspend fun update(words : Word, word:String,commentary: String,correct: Int,wrong: Int) :Word

    // 正解不正解の更新
    suspend fun updateJudgement(word:Word,correct: Int, wrong: Int) :Word

    // 正当数を初期化する
    suspend fun updateInitCorrectNum(word: Word) :Word

    // 全件の正当数を初期化
    suspend fun initCorrectNum(): Int

    // 全件のデータを削除
    suspend fun deleteAll()

    //全件取得のメソッド
    fun getAll(): Flow<List<Word>>

    // 全件のレコード数を取得
    fun getCount(): Flow<Int>

    // 復習対象のメソッド
    fun getReview(): Flow<List<Word>>

    // 復習対象のレコードの行数を取得する
    fun getReviewCount(): Flow<Int>
}