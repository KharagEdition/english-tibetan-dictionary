package com.kharagedition.englishtibetandictionary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.kharagedition.englishtibetandictionary.R
import com.kharagedition.englishtibetandictionary.model.Word

class WordsPagingDataAdapter : PagingDataAdapter<Word, WordsPagingDataAdapter.WordsViewHolder>(
    WordEntityDiff()
) {

    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
        getItem(position)?.let { userPostEntity -> holder.bind(userPostEntity) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.single_list_item,parent,false);
        return WordsViewHolder(view);
    }

    inner class WordsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var wynie: MaterialTextView = itemView.findViewById(R.id.wynie);
        var english: MaterialTextView = itemView.findViewById(R.id.english);
        var defination: MaterialTextView = itemView.findViewById(R.id.defination);
        fun bind(word: Word) {
            wynie.text =word.wylie;
            english.text = word.english;
            defination.text = word.defination;
        }
    }

    class WordEntityDiff : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean = oldItem == newItem
    }
}