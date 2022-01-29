package com.yamato.myflashcard_for_sqlite.page.lessonDetail

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ListAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.yamato.myflashcard_for_sqlite.R
import com.yamato.myflashcard_for_sqlite.databinding.WordLessonDetailFragmentBinding
import com.yamato.myflashcard_for_sqlite.model.Word
import com.yamato.myflashcard_for_sqlite.page.detail.WordDetailFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordLessonDetailFragment: Fragment(R.layout.word_lesson_detail_fragment){
    private val vm: WordLessonDetailViewModel by viewModels()
    private var _binding: WordLessonDetailFragmentBinding? = null
    private val binding: WordLessonDetailFragmentBinding get() = _binding!!

    companion object {
        lateinit var wordList: List<Word>
        lateinit var sortList: ArrayList<Int>
        var countList = 0
        var judge = false
        var TAG = "WordLessonDetailFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = WordLessonDetailFragmentBinding.bind(view)

        // ツールバーの設定
        setHasOptionsMenu(true)
        // ツールバーに戻るボタンを設置
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title = "ランダム出題"

        countList = 0
        sortList = arrayListOf()
        var firstPerformance = true

        // 解説の表示設定：初期値 オフ
        binding.textViewCommentaryLessonDetail.visibility = View.INVISIBLE

        // 解説ボタン
        binding.buttonCommentaryLessonDetail.setOnClickListener{
            //　解説ボタンを押下したら解説を表示する
            binding.textViewCommentaryLessonDetail.visibility = View.VISIBLE
            //　解説ボタンを押下したら解説ボタンは非表示にする
            binding.buttonCommentaryLessonDetail.visibility = View.INVISIBLE
        }

        // わかるボタンを押下したときの処理
        binding.buttonKnowLessonDetail.setOnClickListener{
            judge = true
            addJudgement(judge)
            countList = countList + 1
            fetchLesson(wordList,countList,sortList)
            //　解説ボタンを表示する
            binding.buttonCommentaryLessonDetail.visibility = View.VISIBLE
            //　解説を非表示にする
            binding.textViewCommentaryLessonDetail.visibility = View.INVISIBLE
        }
        // わからないボタンを押下したときの処理
        binding.buttonUnknownLessonDetail.setOnClickListener{
            judge = false
            addJudgement(judge)
            countList = countList + 1
            fetchLesson(wordList,countList,sortList)
            //　解説ボタンを表示する
            binding.buttonCommentaryLessonDetail.visibility = View.VISIBLE
            //　解説を非表示にする
            binding.textViewCommentaryLessonDetail.visibility = View.INVISIBLE
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
    private fun fetchLesson(wordList: List<Word>,countList: Int,sortList: ArrayList<Int>) {
        if (countList < wordList.size) {
//            Log.d(TAG,"wordList " + wordList)
//            Log.d(TAG,"wordList 1件 " + wordList[0])
//            Log.d(TAG,"filter 1件 " + wordList.filter { id == sortList[countList]})
             Log.d(TAG,"countList " + Companion.countList)
            for (list in wordList){
                if (list.id == sortList[countList]) {
                    // 問題数の表示
                    binding.textViewQuestionCntLessonDetail.text = "${Companion.countList + 1} / ${Companion.sortList.size}"
                    //　ランダムの並び順と一致しているデータを返す
                    // 単語の表示
                    binding.textViewWordLessonDetail.text = list.word
                    // 解説の表示
                    binding.textViewCommentaryLessonDetail.text = list.commentary
                }
            }
        }else{
            // 問題終了
            Toast.makeText(context,"問題終了",Toast.LENGTH_SHORT).show()
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
        if (countList < wordList.size) {
            for (list in wordList) {
                if (list.id == sortList[countList]) {
                    // 正当数を保存する
                    vm.addJudgement(list, correct, wrong)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }
}