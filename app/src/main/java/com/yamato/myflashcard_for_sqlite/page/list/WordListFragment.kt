package com.yamato.myflashcard_for_sqlite.page.list

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yamato.myflashcard_for_sqlite.R
import com.yamato.myflashcard_for_sqlite.WordAdapter
import com.yamato.myflashcard_for_sqlite.databinding.MainFragmentBinding
import com.yamato.myflashcard_for_sqlite.databinding.WordListFragmentBinding
import com.yamato.myflashcard_for_sqlite.model.Word
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordListFragment:Fragment(R.layout.word_list_fragment) {
    private val vm: WordListViewModel by viewModels()
    private var _binding: WordListFragmentBinding? = null
    private val binding: WordListFragmentBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //タイトルバーの設定
//        (activity as AppCompatActivity).supportActionBar?.title = "単語リスト"
//        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this._binding = WordListFragmentBinding.bind(view)

        val adapter = WordAdapter{
            // リストがタップされた時の処理
            val action = WordListFragmentDirections.actionWordListFragmentToWordDetailFragment(it)
            findNavController().navigate(action)
        }
        binding.recyclerviewWordList.adapter = adapter

        //LiveDataを監視する
        //リポジトリから新しいリストを受け取ったらsubmitListでアダプターに渡す
        vm.wordList.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }

}