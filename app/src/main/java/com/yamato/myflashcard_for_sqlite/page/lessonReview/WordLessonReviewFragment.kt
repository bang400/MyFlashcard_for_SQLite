package com.yamato.myflashcard_for_sqlite.page.lessonReview

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yamato.myflashcard_for_sqlite.R
import com.yamato.myflashcard_for_sqlite.databinding.WordLessonReviewFragmentBinding
import com.yamato.myflashcard_for_sqlite.model.Word
import com.yamato.myflashcard_for_sqlite.page.lessonDetail.WordLessonDetailFragment
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
        var firstListSize = 0
        var questionCnt = 0
        var TAG = "WordLessonReviewFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = WordLessonReviewFragmentBinding.bind(view)

        // ツールバーの設定
        setHasOptionsMenu(true)
        // ツールバーに戻るボタンを設置
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title = "復習"
        // リスト用の添え字
        countList = 0
        // 今何問か
        questionCnt = 1
        var firstFlg = true

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
            // 今何問か
            questionCnt = questionCnt + 1

            Log.d(TAG,"countListわかる: $countList")
            Log.d(TAG,"wordListわかる: $wordList")

            // 問題の最後のとき
            if(countList + 1 == wordList.size){
                // 問題終了
                Toast.makeText(context,"問題終了", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
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
            // 今何問か
            questionCnt = questionCnt + 1

            Log.d(TAG,"countListわからない: $countList")
            Log.d(TAG,"wordListわからない: $wordList")

            // リストの最後の問題とき
            if(countList + 1 == wordList.size){
                // 問題終了
                Toast.makeText(context,"問題終了", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }

            //　問題数が２問以上あるとき
            if(wordList.size > 1){
                countList = countList + 1
            }else{

            }
        }

        vm.wordList.observe(viewLifecycleOwner) { list ->
            // DBが更新されるたびに呼ばれる
            wordList = list

            if(firstFlg){
                firstListSize = wordList.size
                firstFlg = false
            }
            Log.d(TAG, "wordList: $wordList")
            fetchLesson(wordList,countList)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // ランダム出力時の問題表示
    private fun fetchLesson(wordList: List<Word>, countList: Int) {
        if (wordList.size > countList) {
            // 問題数の表示
            binding.textViewQuestionCntLessonReview.text = "${questionCnt} / ${firstListSize}"
            // 単語の表示
            binding.textViewWordLessonReview.text = wordList[countList].word
            // 解説の表示
            binding.textViewCommentaryLessonReview.text = wordList[countList].commentary
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