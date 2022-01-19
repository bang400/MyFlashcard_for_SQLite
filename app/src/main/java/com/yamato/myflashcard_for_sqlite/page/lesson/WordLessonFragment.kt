package com.yamato.myflashcard_for_sqlite.page.lesson

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yamato.myflashcard_for_sqlite.R
import com.yamato.myflashcard_for_sqlite.databinding.WordLessonFragmentBinding
import com.yamato.myflashcard_for_sqlite.page.list.WordListFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordLessonFragment:Fragment(R.layout.word_lesson_fragment) {

    private var _binding: WordLessonFragmentBinding? = null
    private val binding:WordLessonFragmentBinding get() = _binding!!
    private val vm:WordLessonViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = WordLessonFragmentBinding.bind(view)
        (activity as AppCompatActivity).supportActionBar?.title = "レッスンを選択"

        var reviewCount = 0

        vm.wordCount.observe(viewLifecycleOwner) { it ->
            // 総項目数
            binding.textViewTotalValueWordLesson.text = it.toString()
        }

        // 復習数
        vm.wordReviewCount.observe(viewLifecycleOwner) {it ->
            binding.textViewReviewValueWordLesson.text = it.toString()
            reviewCount = it
        }

        binding.buttonRandomWordLesson.setOnClickListener {
            // ランダム出題
            // 単語テストの画面へ移動
            findNavController().navigate(R.id.action_wordLessonFragment_to_wordLessonDetailFragment)
        }

        binding.buttonReviewWordLesson.setOnClickListener {
            // 復習
            if(reviewCount > 0){
                // 復習数が０でなければ復習画面へ遷移
                findNavController().navigate(R.id.action_wordLessonFragment_to_wordLessonReviewFragment)
            }else{
                Toast.makeText(context,"復習対象の単語はありません",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this._binding = null
    }
}