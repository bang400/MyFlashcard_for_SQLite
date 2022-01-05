package com.yamato.myflashcard_for_sqlite.page.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yamato.myflashcard_for_sqlite.model.Word
import com.yamato.myflashcard_for_sqlite.repository.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordDetailModel @Inject constructor(
    private val repo: WordRepository

):ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val done = MutableLiveData<Word>()

    fun addJudgement(word: Word,right: Int,wrong: Int){
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