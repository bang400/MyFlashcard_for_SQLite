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
    val updateData = MutableLiveData<Word>()
    val notUpdated = MutableLiveData<Boolean>()

    fun updateitem(words: Word, word: String, commentary: String) {
        if (word.trim().isEmpty()){
            errorMessage.value = "単語入力欄に何か入力してください。"
            return
        }
        if (commentary.trim().isEmpty()){
            errorMessage.value = "解説入力欄に何か入力してください。"
            return
        }
        if (word == words.word && commentary == words.commentary){
            notUpdated.value = true
            return
        }
            // 編集された場合のみ、updateする
        viewModelScope.launch {
            try {
                updateData.value = repo.update(words,word,commentary,0,0)
            }catch (e:Exception){
                errorMessage.value = e.message
            }
        }

    }

}