package com.yamato.myflashcard_for_sqlite.page.create

import android.os.Bundle
import android.view.View
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

        (activity as AppCompatActivity).supportActionBar?.title = "単語を追加"

        vm.errorMessage.observe(viewLifecycleOwner) { msg ->
            if (msg.isEmpty()) return@observe
            Snackbar.make(requireView(),msg, Snackbar.LENGTH_SHORT).show()

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
            add()
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