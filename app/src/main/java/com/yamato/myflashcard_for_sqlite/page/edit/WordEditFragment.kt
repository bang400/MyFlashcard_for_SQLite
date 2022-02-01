package com.yamato.myflashcard_for_sqlite.page.edit

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.yamato.myflashcard_for_sqlite.R
import com.yamato.myflashcard_for_sqlite.databinding.WordEditFragmentBinding
import com.yamato.myflashcard_for_sqlite.model.Word
import com.yamato.myflashcard_for_sqlite.page.detail.WordDetailFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordEditFragment:Fragment(R.layout.word_edit_fragment) {
    private val vm: WordEditViewModel by viewModels()
    private val args: WordEditFragmentArgs by navArgs()

    private var _binding: WordEditFragmentBinding? = null
    private val binding: WordEditFragmentBinding get() = _binding!!

    companion object {
        var TAG = "WordEditFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ツールバーの設定
        setHasOptionsMenu(true)
        // タイトルバー設定
        (activity as AppCompatActivity).supportActionBar?.title = "単語編集"
        // ツールバーに戻るボタンを設置
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this._binding = WordEditFragmentBinding.bind(view)

        val words = args.words
        // 単語
        binding.edittextWordEditWord.setText(words.word)
        // 解説
        binding.edittextDetailEditWord.setText(words.commentary)

        binding.buttonUpdateEditWord.setOnClickListener {
            // ボタンクリックのタイミングでキーボードを閉じる
            val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

            // 更新ボタン
            updateItem()
        }
        vm.errorMessage.observe(viewLifecycleOwner){ msg ->
            if (msg.isEmpty()) return@observe
            Snackbar.make(requireView(),msg,Snackbar.LENGTH_SHORT).show()
            vm.errorMessage.value = ""
        }
        // 単語、解説欄の編集がされなかったとき
        vm.notUpdated.observe(viewLifecycleOwner) {
//            findNavController().popBackStack()
            Toast.makeText(context,"単語欄、解説欄の値が編集されていません。",Toast.LENGTH_SHORT).show()
        }

        vm.updateData.observe(viewLifecycleOwner) { it ->
            //　更新完了後の処理
            Toast.makeText(context,"単語を編集しました、",Toast.LENGTH_SHORT).show()
            val action = WordEditFragmentDirections.actionWordEditFragmentToWordDetailFragment(it)
            findNavController().navigate(action)
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

    private fun updateItem() {
        val word = binding.edittextWordEditWord.text.toString()
        val commentary = binding.edittextDetailEditWord.text.toString()
        vm.updateitem(args.words,word,commentary)
    }

    override fun onDestroy() {
        super.onDestroy()
        this._binding = null
    }
}