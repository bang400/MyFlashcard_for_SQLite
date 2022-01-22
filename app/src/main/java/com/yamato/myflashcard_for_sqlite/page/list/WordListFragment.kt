package com.yamato.myflashcard_for_sqlite.page.list

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.yamato.myflashcard_for_sqlite.R
import com.yamato.myflashcard_for_sqlite.WordAdapter
import com.yamato.myflashcard_for_sqlite.databinding.WordListFragmentBinding
import com.yamato.myflashcard_for_sqlite.model.Word
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordListFragment:Fragment(R.layout.word_list_fragment) {
    private val vm: WordListViewModel by viewModels()
    private var _binding: WordListFragmentBinding? = null
    private val binding: WordListFragmentBinding get() = _binding!!

    companion object {
        lateinit var wordList: MutableList<Word>
        var TAG = "WordLessonListFragment"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //隠したい
//        (activity as AppCompatActivity?)!!.getSupportActionBar()!!.show()
        (activity as AppCompatActivity).supportActionBar?.title = "単語リスト"
        setHasOptionsMenu(true)
        setFragmentResultListener("confirm"){_,data ->
            val which = data.getInt("result")
            if (which == DialogInterface.BUTTON_POSITIVE){
                vm.initCorrectNumFun()
                Log.d("WordListFragment","正当数を初期化する")
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //タイトルバーの設定
        this._binding = WordListFragmentBinding.bind(view)

        (activity as AppCompatActivity).supportActionBar?.title = "単語リスト"

        val adapter = WordAdapter{
            // リストがタップされた時の処理
            val action = WordListFragmentDirections.actionWordListFragmentToWordDetailFragment(it)
            findNavController().navigate(action)
        }
        //LiveDataを監視する
        //リポジトリから新しいリストを受け取ったらsubmitListでアダプターに渡す
        vm.wordList.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
//            wordList = list
//            Log.d("TEST","よびました")
        }
        // リストの区切り線を表示
        val itemDecoration = DividerItemDecoration(context,DividerItemDecoration.VERTICAL)
        binding.recyclerviewWordList.addItemDecoration(itemDecoration)

        binding.recyclerviewWordList.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_detail,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_study_data_delete -> {
                findNavController().navigate(
                    R.id.action_wordListFragment_to_wordConfirmDialogFragment
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }

}