package com.yamato.myflashcard_for_sqlite

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.*
import com.yamato.myflashcard_for_sqlite.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment:Fragment(R.layout.main_fragment) {

    // コミットテスト
    private val vm: MainViewModel by viewModels()
    private var _binding: MainFragmentBinding? = null
    private val binding: MainFragmentBinding get() = _binding!!
    lateinit var mAdView: AdView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = MainFragmentBinding.bind(view)

        // GoogleAdmob設定
        MobileAds.initialize(context) {}
        mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        mAdView.adListener = object: AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }

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