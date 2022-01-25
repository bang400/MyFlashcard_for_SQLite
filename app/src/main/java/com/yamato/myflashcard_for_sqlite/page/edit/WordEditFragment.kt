package com.yamato.myflashcard_for_sqlite.page.edit

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.yamato.myflashcard_for_sqlite.R
import com.yamato.myflashcard_for_sqlite.databinding.WordEditFragmentBinding
import com.yamato.myflashcard_for_sqlite.page.detail.WordDetailFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordEditFragment:Fragment(R.layout.word_edit_fragment) {
    private val vm: WordEditViewModel by viewModels()
    private val args: WordEditFragmentArgs by navArgs()

    private var _binding: WordEditFragmentBinding? = null
    private val binding: WordEditFragmentBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // タイトルバー設定
        (activity as AppCompatActivity).supportActionBar?.title = "単語編集"
        this._binding = WordEditFragmentBinding.bind(view)

        val words = args.words
        // 単語
        binding.edittextWordEditWord.setText(words.word)
        // 解説
        binding.edittextDetailEditWord.setText(words.commentary)

        binding.buttonUpdateEditWord.setOnClickListener {
            // 更新ボタン
            updateItem()
        }
        vm.errorMessage.observe(viewLifecycleOwner){ msg ->
            if (msg.isEmpty()) return@observe
            Snackbar.make(requireView(),msg,Snackbar.LENGTH_SHORT).show()
            vm.errorMessage.value = ""
        }
        vm.updated.observe(viewLifecycleOwner) { it ->
            //　更新完了後の処理
            Toast.makeText(context,"単語を編集しました、",Toast.LENGTH_SHORT).show()
            val action = WordEditFragmentDirections.actionWordEditFragmentToWordDetailFragment(it)
            findNavController().navigate(action)
//            findNavController().popBackStack(R.id.wordDetailFragment,false)
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