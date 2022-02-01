package com.yamato.myflashcard_for_sqlite.page.list

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yamato.myflashcard_for_sqlite.model.Word
import com.yamato.myflashcard_for_sqlite.repository.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class WordListViewModel @Inject constructor(
    // レポジトリを受け取る
    private val repo: WordRepository
):ViewModel() {

    // LiveDataをプロパティとしてもたせる
    val wordList = repo.getAll().asLiveData()
    val erroMessage = MutableLiveData<String>()
    val deleted = MutableLiveData<Boolean>()
    val inited = MutableLiveData<Boolean>()

    fun initCorrectNumFun() {
        viewModelScope.launch {
            try {
                Log.d("WordListViewModel","before repo")
                repo.initCorrectNum()
                inited.value = true
            }catch (e: Exception){
                erroMessage.value = e.message
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            try {
                Log.d("WordListViewModel","before repo")
                repo.deleteAll()
                deleted.value = true
            }catch (e: Exception){
                erroMessage.value = e.message
            }
        }
    }

}