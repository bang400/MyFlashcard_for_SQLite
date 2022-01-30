package com.yamato.myflashcard_for_sqlite.page.detail

import android.util.Log
import androidx.lifecycle.*
import com.yamato.myflashcard_for_sqlite.model.Word
import com.yamato.myflashcard_for_sqlite.repository.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordDetailModel @Inject constructor(
    private val repo: WordRepository,
    savedStateHandle: SavedStateHandle
):ViewModel() {
    val words = savedStateHandle.getLiveData<Word>("word")
    val errorMessage = MutableLiveData<String>()
    val done = MutableLiveData<Word>()
    val deleted = MutableLiveData<Boolean>()
    val deletedNothingData = MutableLiveData<Boolean>()
    val allCnt = repo.getCount().asLiveData()
    val TAG = "WordDetailModel"

    fun addJudgement(word: Word,correct: Int,wrong: Int){
        // 単語詳細画面にて既知、未知の判定結果の処理
        viewModelScope.launch {
            try {
                val updatedWord = repo.updateJudgement(word,correct,wrong)
                done.value = updatedWord
            } catch (e: Exception) {
                errorMessage.value = e.toString()
            }
        }
    }
    fun delete() {
        // 単語１件削除の処理
        viewModelScope.launch {
            try{
                val word = this@WordDetailModel.words.value ?: return@launch
                repo.delete(word)
                deleted.value = true
                // 単語が０なら
//                val allCnt = repo.getCount().toString()
//                Log.d(TAG,"削除ボタン押下後のリスト個数$allCnt")
//                if (allCnt.toInt() > 1){
//
//                }else{
//                    deletedNothingData.value = true
//                }
            }catch (e:Exception){
                errorMessage.value = e.message
            }
        }
    }
}