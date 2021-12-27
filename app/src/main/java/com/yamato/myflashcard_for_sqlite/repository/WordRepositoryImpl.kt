package com.yamato.myflashcard_for_sqlite.repository

import com.yamato.myflashcard_for_sqlite.model.Word
import com.yamato.myflashcard_for_sqlite.model.WordDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(
    private val dao: WordDAO
    ): WordRepository {

    override fun getAll(): Flow<List<Word>> {
        return dao.getAll()
    }

    override suspend fun create(word: String, commentary: String) {
        // サーバに書き換えしたかったらこのメソッドを変更するだけで良し
        val nowTime = System.currentTimeMillis()
        val words = Word(word = word, commentary = commentary,created = nowTime,modified = nowTime,right = 0,wrong = 0)
        withContext(Dispatchers.IO) {
            dao.create(words)
        }
    }
}