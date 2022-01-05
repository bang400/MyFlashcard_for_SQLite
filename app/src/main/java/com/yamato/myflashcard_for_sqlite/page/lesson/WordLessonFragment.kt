package com.yamato.myflashcard_for_sqlite.page.lesson

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.yamato.myflashcard_for_sqlite.R
import com.yamato.myflashcard_for_sqlite.databinding.WordLessonFragmentBinding

class WordLessonFragment:Fragment(R.layout.word_lesson_fragment) {

    private var _binding: WordLessonFragmentBinding? = null
    private val binding:WordLessonFragmentBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = WordLessonFragmentBinding.bind(view)

        // 総項目数
        binding.textViewTotalValueWordLesson.text = "200"

        // 復習数
        binding.textViewReviewValueWordLesson.text = "30"

        binding.buttonRandomWordLesson.setOnClickListener {
            // ランダム出題
        }

        binding.buttonReviewWordLesson.setOnClickListener {
            // 復習
        }
    }

}