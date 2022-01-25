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

    override fun getCount(): Flow<Int> {
        return dao.getCount()
    }

    override fun getReview(): Flow<List<Word>> {
        return dao.getReview()
    }

    override fun getReviewCount(): Flow<Int> {
        return dao.getReviewCount()
    }

    override fun initCorrectNum(): Int {
        return dao.initCorrectNum()
    }

    override suspend fun create(word: String, commentary: String) {
        // サーバに書き換えしたかったらこのメソッドを変更するだけで良し
        val nowTime = System.currentTimeMillis()
        val words = Word(word = word, commentary = commentary,created = nowTime,modified = nowTime,correct = 0,wrong = 0)
        withContext(Dispatchers.IO) {
            dao.create(words)
        }
    }

    override suspend fun updateJudgement(word:Word,correct: Int, wrong: Int): Word {
        val updatedWord = Word(
            id = word.id,
            word = word.word,
            commentary = word.commentary,
            correct = word.correct + correct,
            wrong = word.wrong + wrong,
            created = word.created,
            modified = System.currentTimeMillis()
        )
        withContext(Dispatchers.IO) {
            dao.update(updatedWord)
        }
        return updatedWord
    }

    override suspend fun updateInitCorrectNum(word: Word): Word {
        val updateInitWord = Word(
            id = word.id,
            word = word.word,
            commentary = word.commentary,
            correct = 0,
            wrong = 0,
            created = word.created,
            modified = System.currentTimeMillis()
        )
        withContext(Dispatchers.IO){
            dao.update(updateInitWord)
        }
        return updateInitWord
    }

    override suspend fun update(words: Word, word: String, commentary: String,correct: Int,wrong: Int): Word {
        val updatedWords = Word(
            id = words.id,
            word = word,
            commentary = commentary,
            correct = correct,
            wrong = wrong,
            created = words.created,
            modified = System.currentTimeMillis()
        )
        withContext(Dispatchers.IO) {
            dao.update(updatedWords)
        }
        return updatedWords
    }

    override suspend fun delete(word: Word) {
        dao.delete(word)
    }
}