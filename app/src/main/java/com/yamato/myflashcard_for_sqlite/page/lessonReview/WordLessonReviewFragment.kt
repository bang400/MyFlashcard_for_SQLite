package com.yamato.myflashcard_for_sqlite.page.lessonReview

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yamato.myflashcard_for_sqlite.R
import com.yamato.myflashcard_for_sqlite.databinding.WordLessonReviewFragmentBinding
import com.yamato.myflashcard_for_sqlite.model.Word
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordLessonReviewFragment:Fragment(R.layout.word_lesson_review_fragment) {
    private val vm: WordLessonReviewViewModel by viewModels()
    private var _binding: WordLessonReviewFragmentBinding? = null
    private val binding: WordLessonReviewFragmentBinding get() = _binding!!

    companion object {
        lateinit var wordList: List<Word>
        var countList = 0
        var judge = false
        var TAG = "WordLessonReviewFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = WordLessonReviewFragmentBinding.bind(view)

        countList = 0

        // 解説の表示設定：初期値 オフ
        binding.textViewCommentaryLessonReview.visibility = View.INVISIBLE

        // 解説ボタン
        binding.buttonCommentaryLessonReview.setOnClickListener{
            //　解説ボタンを押下したら解説を表示する
            binding.textViewCommentaryLessonReview.visibility = View.VISIBLE
            //　解説ボタンを押下したら解説ボタンは非表示にする
            binding.buttonCommentaryLessonReview.visibility = View.INVISIBLE
        }

        // わかるボタンを押下したときの処理
        binding.buttonKnowLessonReview.setOnClickListener{
            judge = true
            addJudgement(judge)
            fetchLesson(wordList,countList)
            //　解説ボタンを表示する
            binding.buttonCommentaryLessonReview.visibility = View.VISIBLE
            //　解説を非表示にする
            binding.textViewCommentaryLessonReview.visibility = View.INVISIBLE
            countList = 0
        }
        // わからないボタンを押下したときの処理
        binding.buttonUnknownLessonReview.setOnClickListener{
            judge = false
            addJudgement(judge)
            fetchLesson(wordList,countList)
            //　解説ボタンを表示する
            binding.buttonCommentaryLessonReview.visibility = View.VISIBLE
            //　解説を非表示にする
            binding.textViewCommentaryLessonReview.visibility = View.INVISIBLE
            if(wordList.size > 1){
                countList = countList + 1
            }else{
                countList = 0
                // 問題終了
                Toast.makeText(context,"問題終了", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }

        vm.wordList.observe(viewLifecycleOwner) { list ->
            // DBが更新されるたびに呼ばれる
            wordList = list
            Log.d(TAG, "wordList: $wordList")
            fetchLesson(wordList,countList)
        }
    }

    // ランダム出力時の問題表示
    private fun fetchLesson(wordList: List<Word>, countList: Int) {
        Log.d(TAG,"wordList.size: " + wordList.size)
        if (wordList.size > countList) {
            // 単語の表示
            binding.textViewWordLessonReview.text = wordList[countList].word
            // 解説の表示
            binding.textViewCommentaryLessonReview.text = wordList[countList].commentary
        }else{
            // 問題終了
            Toast.makeText(context,"問題終了", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

    private fun addJudgement(judge:Boolean) {
        var correct = 0
        var wrong = 0

        if(judge){
            // わかる
            correct = 1
        }else{
            //　わからない
            wrong = 1
        }
        if (wordList.size > countList) {
            // 正当数を保存する
            vm.addJudgement(wordList[countList], correct, wrong)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }

}