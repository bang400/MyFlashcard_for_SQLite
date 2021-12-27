package com.yamato.myflashcard_for_sqlite.model

import androidx.room.Database
import androidx.room.RoomDatabase

// アノテーションの引数にはファイルにどのテーブルを含めるかentitiesで指定する
// exportSchemaでエクスポートするフォルダがないときに起こるエラーを回避する
@Database(entities = [Word::class], version = 1,exportSchema = false)
abstract class WordDatabase: RoomDatabase() {
    // 抽象
    abstract fun wordDAO(): WordDAO
}