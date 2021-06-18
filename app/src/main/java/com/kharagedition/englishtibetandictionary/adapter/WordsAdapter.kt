package com.kharagedition.englishtibetandictionary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.kharagedition.englishtibetandictionary.R
import com.kharagedition.englishtibetandictionary.model.Word

/**
 * Created by kharag on 18,June,2021
 */

class WordsAdapter(var listener: ItemClickListener) : ListAdapter<Word, WordsAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_list_item,parent,false);
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem,listener);
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var wynie: MaterialTextView = itemView.findViewById(R.id.wynie);
        var english: MaterialTextView = itemView.findViewById(R.id.english);
        var defination: MaterialTextView = itemView.findViewById(R.id.defination);

        fun bind(
                word: Word?,
                listener: ItemClickListener,
        ) {
            wynie.text =word?.wylie;
            english.text = word?.english;
            defination.text = word?.defination;
            itemView.setOnClickListener {
                if (word != null) {
                    listener.onItemClick(word = word)
                }

            }
        }


    }

    class DiffCallback : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word) =
                oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Word, newItem: Word) =
                oldItem == newItem
    }

}
interface ItemClickListener{
    fun onItemClick(word:Word);
}