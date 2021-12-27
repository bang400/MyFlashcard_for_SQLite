package com.yamato.myflashcard_for_sqlite.page.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yamato.myflashcard_for_sqlite.repository.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WordListViewModel @Inject constructor(
    // レポジトリを受け取る
    private val repo: WordRepository
):ViewModel() {
    // LiveDataをプロパティとしてもたせる
    val wordList = repo.getAll().asLiveData()
}