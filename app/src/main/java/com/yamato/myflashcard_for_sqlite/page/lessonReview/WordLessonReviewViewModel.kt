package com.yamato.myflashcard_for_sqlite.page.lessonReview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yamato.myflashcard_for_sqlite.model.Word
import com.yamato.myflashcard_for_sqlite.repository.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordLessonReviewViewModel @Inject constructor(
    private val repo : WordRepository
): ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val done = MutableLiveData<Word>()

    // ランダム出題用として使用
    val wordList = repo.getAll().asLiveData()
    // 復習出題用として使用
    val wordReviewList = repo.getReview().asLiveData()

    fun addJudgement(word: Word, right: Int, wrong: Int){
        viewModelScope.launch {
            try {
                val updatedWord = repo.updateJudgement(word,right,wrong)
                done.value = updatedWord
            } catch (e: Exception) {
                errorMessage.value = e.toString()
            }
        }
    }
}