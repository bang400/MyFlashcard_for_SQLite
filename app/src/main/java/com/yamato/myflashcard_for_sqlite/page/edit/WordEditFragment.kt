package com.yamato.myflashcard_for_sqlite.page.edit

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yamato.myflashcard_for_sqlite.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordEditFragment:Fragment(R.layout.word_edit_fragment) {
    private val vm: WordEditViewModel by viewModels()
}