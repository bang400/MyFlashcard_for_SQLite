package com.yamato.myflashcard_for_sqlite.page.lesson

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yamato.myflashcard_for_sqlite.R
import com.yamato.myflashcard_for_sqlite.databinding.WordLessonFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordLessonFragment:Fragment(R.layout.word_lesson_fragment) {

    private var _binding: WordLessonFragmentBinding? = null
    private val binding:WordLessonFragmentBinding get() = _binding!!
    private val vm:WordLessonViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = WordLessonFragmentBinding.bind(view)

        vm.wordCount.observe(viewLifecycleOwner) { it ->
            // 総項目数
            binding.textViewTotalValueWordLesson.text = it.toString()
        }

        // 復習数
        vm.wordReviewCount.observe(viewLifecycleOwner) {it ->
            binding.textViewReviewValueWordLesson.text = it.toString()
        }

        binding.buttonRandomWordLesson.setOnClickListener {
            // ランダム出題
            // 単語テストの画面へ移動
            findNavController().navigate(R.id.action_wordLessonFragment_to_wordLessonDetailFragment)
        }

        binding.buttonReviewWordLesson.setOnClickListener {
            // 復習
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this._binding = null
    }
}