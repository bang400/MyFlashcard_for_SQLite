package com.yamato.myflashcard_for_sqlite

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yamato.myflashcard_for_sqlite.databinding.WordItemBinding
import com.yamato.myflashcard_for_sqlite.model.Word

class WordAdapter(
    private val listener: (Word) -> Unit
): ListAdapter<Word, WordAdapter.ViewHolder>(callbacks) {

    // 1行分のビューを作り、ViewHolderに設定する
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = WordItemBinding.inflate(inflater, parent, false)

        val viewHolder = ViewHolder(binding)
        binding.root.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition
            val words = getItem(position)
            listener(words)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val word = getItem(position)
        holder.bindTo(word)
    }

    class ViewHolder(
        private val binding: WordItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        // 設定メソッド
        fun bindTo(word: Word) {
            // 単語ID
            binding.textviewWordNumWordItem.text = "No." + word.id.toString()
            // 単語
            binding.textViewWordWordItem.text = word.word
            // 正解数
            binding.textViewCorrectWordItem.text = word.correct.toString()
            // 誤答数
            binding.textViewWrongWordItem.text = word.wrong.toString()
//            Log.d("WordAdapter",word.word)
        }
    }

    companion object {
        private val callbacks = object : DiffUtil.ItemCallback<Word>() {

            //同じアイテムかどうか比較
            override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem.id == newItem.id
            }

            //同じアイテムの時に表示内容が同じかどうか
            override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem.word == newItem.word &&
                        oldItem.created == newItem.created &&
                        oldItem.correct == newItem.correct &&
                        oldItem.wrong == newItem.wrong
            }
        }
    }
}