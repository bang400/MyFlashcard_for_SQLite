package com.yamato.myflashcard_for_sqlite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yamato.myflashcard_for_sqlite.repository.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: WordRepository
):ViewModel() {
    val wordCount = repo.getCount().asLiveData()
}