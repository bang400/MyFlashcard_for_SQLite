package com.yamato.myflashcard_for_sqlite.page.create

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.yamato.myflashcard_for_sqlite.R
import com.yamato.myflashcard_for_sqlite.WordAdapter
import com.yamato.myflashcard_for_sqlite.databinding.WordAddFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordAddFragment:Fragment(R.layout.word_add_fragment) {
    private val vm: WordAddViewModel by viewModels()
    private var _binding: WordAddFragmentBinding? = null
    private val binding: WordAddFragmentBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = WordAddFragmentBinding.bind(view)

        // ツールバーの設定
        setHasOptionsMenu(true)
        // ツールバーに戻るボタンを設置
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title = "単語を追加"

        vm.errorMessage.observe(viewLifecycleOwner) { msg ->
            if (msg.isEmpty()) return@observe
//            Snackbar.make(requireView(),msg, Snackbar.LENGTH_SHORT).show()
            Toast.makeText(context,"$msg",Toast.LENGTH_SHORT).show()
            // 表示後は中身を空っぽにしておく。
            // これをしないと、画面回転する度にSnackbarが表示されてしまう
            vm.errorMessage.value = ""
        }

        // 保存処理が完了したらリストを表示する
        vm.done.observe(viewLifecycleOwner){
            //　単語追加画面　→　単語リスト画面
//            findNavController().popBackStack()
            findNavController().navigate(R.id.action_wordAddFragment_to_wordListFragment)
        }

        binding.buttonAddAddWord.setOnClickListener {
            // ボタンクリックのタイミングでキーボードを閉じる
            val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            add()
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

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }

    private fun add() {
        val word = binding.edittextWordAddWord.text.toString()
        val commentary = binding.edittextDetailAddWord.text.toString()
        vm.add(word,commentary)
    }
}