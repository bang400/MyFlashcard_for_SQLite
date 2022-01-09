package com.yamato.myflashcard_for_sqlite.page.lessonReview

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
        lateinit var sortList: ArrayList<Int>
        var countList = 0
        var judge = false
        var TAG = "WordLessonReviewFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = WordLessonReviewFragmentBinding.bind(view)

        countList = 0
        sortList = arrayListOf()
        var firstPerformance = true

        // 解説ボタンの表示設定：オン
        binding.textViewCommentaryLessonReview.visibility = View.INVISIBLE

        // 解説ボタン
        binding.buttonCommentaryLessonReview.setOnClickListener{
            //　解説ボタンを押下したら解説を表示する
            binding.textViewCommentaryLessonReview.visibility = View.VISIBLE
        }

        // わかるボタンを押下したときの処理
        binding.buttonKnowLessonReview.setOnClickListener{
            judge = true
            addJudgement(judge)
            fetchLesson(wordList,countList,sortList)
            //　解説ボタンを表示する
            binding.buttonCommentaryLessonReview.visibility = View.VISIBLE
            //　解説を非表示にする
            binding.textViewCommentaryLessonReview.visibility = View.INVISIBLE
            countList = countList + 1
        }
        // わからないボタンを押下したときの処理
        binding.buttonUnknownLessonReview.setOnClickListener{
            judge = false
            addJudgement(judge)
            fetchLesson(wordList,countList,sortList)
            //　解説ボタンを表示する
            binding.buttonCommentaryLessonReview.visibility = View.VISIBLE
            //　解説を非表示にする
            binding.textViewCommentaryLessonReview.visibility = View.INVISIBLE
            countList = countList + 1
        }

        vm.wordList.observe(viewLifecycleOwner) { list ->
            // DBが更新されるたびに呼ばれる
            wordList = list

            if(firstPerformance){
                // はじめに呼ばれる際にランダムに並び替える順番を保存
                wordList = wordList.shuffled()
                // 配列の順番だけ保存する
                for (i in wordList){
                    sortList.add(i.id)
                }
                fetchLesson(wordList,countList,sortList)
                // 一度だけ呼ばれるためfalseにしておく
                firstPerformance = false
            }

        }
    }

    // ランダム出力時の問題表示
    private fun fetchLesson(wordList: List<Word>, countList: Int, sortList: ArrayList<Int>) {
        if (countList < wordList.size) {
//            Log.d(TAG,"wordList " + wordList)
//            Log.d(TAG,"wordList 1件 " + wordList[0])
//            Log.d(TAG,"filter 1件 " + wordList.filter { id == sortList[countList]})
            Log.d(TAG,"countList " + Companion.countList)
            for (list in wordList){
                if (list.id == sortList[countList]) {
                    //　ランダムの並び順と一致しているデータを返す
                    // 単語の表示
                    binding.textViewWordLessonReview.text = list.word
                    // 解説の表示
                    binding.textViewCommentaryLessonReview.text = list.commentary
                }
            }
        }else{
            // 問題終了
            Toast.makeText(context,"問題終了", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addJudgement(judge:Boolean) {
        var right = 0
        var wrong = 0

        if(judge){
            // わかる
            right = 1
        }else{
            //　わからない
            wrong = 1
        }
        if (countList < wordList.size) {
            for (list in wordList) {
                if (list.id == sortList[countList]) {
                    // 正当数を保存する
                    vm.addJudgement(list, right, wrong)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }

}