package com.yamato.myflashcard_for_sqlite

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yamato.myflashcard_for_sqlite.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment:Fragment(R.layout.main_fragment) {

    private val vm: MainViewModel by viewModels()
    private var _binding: MainFragmentBinding? = null
    private val binding: MainFragmentBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = MainFragmentBinding.bind(view)

        //隠したい
//        (activity as AppCompatActivity?)!!.getSupportActionBar()!!.hide()
        (activity as AppCompatActivity).supportActionBar?.title = "ホーム"
        // ツールバー戻るボタンを非表示にする
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        var count = 0

        vm.wordCount.observe(viewLifecycleOwner) { it ->
            count = it
        }

        binding.buttonAddwordMain.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_wordAddFragment)
//            Toast.makeText(context, "Move Successfully",Toast.LENGTH_SHORT).show()
        }

        binding.buttonListMain.setOnClickListener {
            // 単語リストを表示する
            if (count != 0){
                findNavController().navigate(R.id.action_mainFragment_to_wordListFragment)
            }else{
                Toast.makeText(context,"単語を追加してください",Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonLessonMain.setOnClickListener {
            // レッスン画面へ遷移する
            if (count != 0){
                findNavController().navigate(R.id.action_mainFragment_to_wordLessonFragment)
            }else{
                Toast.makeText(context,"単語を追加してください",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }
}