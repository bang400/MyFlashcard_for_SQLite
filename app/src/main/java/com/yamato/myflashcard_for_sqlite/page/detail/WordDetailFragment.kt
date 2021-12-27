package com.yamato.myflashcard_for_sqlite.page.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.yamato.myflashcard_for_sqlite.R
import com.yamato.myflashcard_for_sqlite.databinding.WordDetailFragmentBinding

class WordDetailFragment:Fragment(R.layout.word_detail_fragment) {

    private val vm:WordDetailModel by viewModels()
    private var _binding: WordDetailFragmentBinding? = null
    private val binding: WordDetailFragmentBinding get() = _binding!!

    private val args: WordDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = WordDetailFragmentBinding.bind(view)

        // リストのアイテムから受け取った単語の表示
        val words = args.words
        binding.textViewWordWordDetail.text = words.word
    }
}