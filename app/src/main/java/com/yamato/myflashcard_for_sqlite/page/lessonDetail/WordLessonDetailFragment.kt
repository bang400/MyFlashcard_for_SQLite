package com.yamato.myflashcard_for_sqlite.page.lessonDetail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.yamato.myflashcard_for_sqlite.R
import com.yamato.myflashcard_for_sqlite.databinding.WordLessonDetailFragmentBinding
import com.yamato.myflashcard_for_sqlite.model.Word
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordLessonDetailFragment: Fragment(R.layout.word_lesson_detail_fragment){
    private val vm: WordLessonDetailViewModel by viewModels()
    private var _binding: WordLessonDetailFragmentBinding? = null
    private val binding: WordLessonDetailFragmentBinding get() = _binding!!

    companion object {
        lateinit var wordList: List<Word>
        var countList = 1
        var judge = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = WordLessonDetailFragmentBinding.bind(view)

        // 解説ボタンの表示設定：オン
        binding.textViewCommentaryLessonDetail.visibility = View.INVISIBLE

        // 解説ボタン
        binding.buttonCommentaryLessonDetail.setOnClickListener{
            //　解説ボタンを押下したら解説を表示する
            binding.textViewCommentaryLessonDetail.visibility = View.VISIBLE
        }

        // わかるボタンを押下したときの処理
        binding.buttonKnowLessonDetail.setOnClickListener{
            judge = true
            countList = countList + 1
            addJudgement(judge)
            fetchLesson(wordList,countList)
            //　解説ボタンを表示する
            binding.buttonCommentaryLessonDetail.visibility = View.VISIBLE
            //　解説を非表示にする
            binding.textViewCommentaryLessonDetail.visibility = View.INVISIBLE
        }
        // わからないボタンを押下したときの処理
        binding.buttonUnknownLessonDetail.setOnClickListener{
            judge = false
            countList = countList + 1
            addJudgement(judge)
            fetchLesson(wordList,countList)
            //　解説ボタンを表示する
            binding.buttonCommentaryLessonDetail.visibility = View.VISIBLE
            //　解説を非表示にする
            binding.textViewCommentaryLessonDetail.visibility = View.INVISIBLE
        }

        vm.wordList.observe(viewLifecycleOwner) { list ->
            wordList = list
            fetchLesson(wordList,countList)
        }

    }

    private fun fetchLesson(wordList: List<Word>,countList: Int) {
        var filterList = wordList.filter { it.id == countList}
//        Log.d("TAG","$filterList")
        for(it in filterList){
            // 単語の表示
            binding.textViewWordLessonDetail.text = it.word
            // 解説の表示
            binding.textViewCommentaryLessonDetail.text = it.commentary
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
        // 正当数を保存する
//        vm.addJudgement(args.words,right,wrong)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }
}