package com.yamato.myflashcard_for_sqlite.page.edit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.yamato.myflashcard_for_sqlite.R
import com.yamato.myflashcard_for_sqlite.databinding.WordEditFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordEditFragment:Fragment(R.layout.word_edit_fragment) {
    private val vm: WordEditViewModel by viewModels()
    private val args: WordEditFragmentArgs by navArgs()

    private var _binding: WordEditFragmentBinding? = null
    private val binding: WordEditFragmentBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = WordEditFragmentBinding.bind(view)

        val words = args.words
    }
}