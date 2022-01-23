package com.yamato.myflashcard_for_sqlite.page.create

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yamato.myflashcard_for_sqlite.repository.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class WordAddViewModel @Inject constructor(
    //　viewModelに引数のあるコンストラクタを追加すると、アプリはクラッシュしてしまう。
    // viewModel用Hiltライブラリを使用することで回避できる
    private val repo: WordRepository
): ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val done = MutableLiveData<Boolean>()

    fun add(word: String, commentary:String) {
        if (word.trim().isEmpty()){
            errorMessage.value = "単語入力欄が空です"
            return
        }
        if(commentary.trim().isEmpty()){
            errorMessage.value = "解説入力欄が空です。"
            return
        }
        //　リポジトリ経由で実際の保存処理を行う
        //　保存してあるかどうか確認するためにDatabaseInspectorが必要
        // 保存処理は非同期で行われるので、ViewModelScopeの中で実行する
        viewModelScope.launch {
            try {
                //保存処理中に画面回転が発生しても処理は継続されて、
                // バックボタンなどでViewModelが破棄されるタイミングでキャンセルされるようになります
                repo.create(word,commentary)
                done.value = true
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }
}