package com.yamato.myflashcard_for_sqlite.page.lesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yamato.myflashcard_for_sqlite.repository.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WordLessonViewModel @Inject constructor(
    private val repo: WordRepository
):ViewModel() {
    // 全件
    val wordCount = repo.getCount().asLiveData()
    // 復習対象
    val wordReviewCount = repo.getReviewCount().asLiveData()
}