package com.yamato.myflashcard_for_sqlite.page.detail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.yamato.myflashcard_for_sqlite.R
import com.yamato.myflashcard_for_sqlite.databinding.WordDetailFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordDetailFragment:Fragment(R.layout.word_detail_fragment), View.OnClickListener {

    private val vm:WordDetailModel by viewModels()
    private var _binding: WordDetailFragmentBinding? = null
    private val binding: WordDetailFragmentBinding get() = _binding!!

    private val args: WordDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = WordDetailFragmentBinding.bind(view)

        // リストのアイテムから受け取った単語の表示
        val words = args.words
        // テキスト：単語
        binding.textViewWordWordDetail.text = words.word

        // テキスト：解説
        binding.textViewCommentaryWordDetail.text = words.commentary
        binding.textViewCommentaryWordDetail.visibility = View.INVISIBLE

        // 解説ボタン
        binding.buttonCommentaryWordDetail.setOnClickListener(this)
        // わかるボタン
        binding.buttonKnowWordDetail.setOnClickListener(this)
        // わからないボタン
        binding.buttonUnknownWordDetail.setOnClickListener(this)

        vm.errorMessage.observe(viewLifecycleOwner) { msg ->
            if (msg.isEmpty()) return@observe
            Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT).show()
            vm.errorMessage.value = ""
        }
        vm.done.observe(viewLifecycleOwner) {
            // リストへ遷移させる
            findNavController().popBackStack()
        }
    }

    override fun onClick(p0: View?) {
        var judge = false

        if (R.id.button_know_word_detail == p0?.id) {
            // わかるボタン
            judge = true
            addJudgement(judge)
        }else if(R.id.button_unknown_word_detail == p0?.id) {
            // わからないボタン
            judge = false
            addJudgement(judge)
        }else if(R.id.button_commentary_word_detail == p0?.id){
            // 解説ボタン
            // 解説ボタンを押下したらボタンを非表示にする
            binding.buttonCommentaryWordDetail.visibility = View.INVISIBLE
            //　解説ボタンを押下したら解説を表示する
            binding.textViewCommentaryWordDetail.visibility = View.VISIBLE
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
        // 正当数を保存する
        vm.addJudgement(args.words,correct,wrong)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }
}