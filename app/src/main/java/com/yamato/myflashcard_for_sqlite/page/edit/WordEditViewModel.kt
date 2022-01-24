package com.yamato.myflashcard_for_sqlite.page.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yamato.myflashcard_for_sqlite.model.Word
import com.yamato.myflashcard_for_sqlite.repository.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordEditViewModel @Inject constructor(
    private val repo: WordRepository
):ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val updated = MutableLiveData<Boolean>()

    fun updateitem(words: Word, word: String, commentary: String) {
        if (word.trim().isEmpty()){
            errorMessage.value = "単語入力欄に何か入力してください。"
            return
        }
        if (commentary.trim().isEmpty()){
            errorMessage.value = "解説入力欄に何か入力してください。"
            return
        }
        viewModelScope.launch {
            try {
                repo.update(words,word,commentary)
                updated.value = true
            }catch (e:Exception){
                errorMessage.value = e.message
            }
        }

    }

}