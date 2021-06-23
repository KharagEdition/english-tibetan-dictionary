package com.kharagedition.tibetandictionary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.kharagedition.tibetandictionary.R
import com.kharagedition.tibetandictionary.model.Word
import com.kharagedition.tibetandictionary.viewmodel.WordsViewModel

val WORD_COMPARATOR = object : DiffUtil.ItemCallback<Word>() {
    override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean =
            // User ID serves as unique ID
            oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean =
            // Compare full contents (note: Java users should call .equals())
            oldItem == newItem
}
class WordsPagingDataAdapter(var wordViewModel: WordsViewModel) : PagingDataAdapter<Word, WordsPagingDataAdapter.WordsViewHolder>(
        WORD_COMPARATOR
) {

    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
        getItem(position)?.let { userPostEntity -> holder.bind(userPostEntity) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.single_list_item,parent,false)
        return WordsViewHolder(view)
    }

    inner class WordsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var wynie: MaterialTextView = itemView.findViewById(R.id.wynie)
        var english: MaterialTextView = itemView.findViewById(R.id.english)
        var defination: MaterialTextView = itemView.findViewById(R.id.defination)
        var addToFavIcon: ImageView = itemView.findViewById(R.id.add_fav_icon_white)
        fun bind(word: Word) {
            wynie.text =word.wylie
            english.text = word.english
            defination.text = word.defination
            if(word.favourite!=null && word.favourite==true){
                addToFavIcon.setImageDrawable(ContextCompat.getDrawable(itemView.context,R.drawable.ic_baseline_favorite_24_red))
            }else{
                addToFavIcon.setImageDrawable(ContextCompat.getDrawable(itemView.context,R.drawable.ic_baseline_favorite_24))

            }
            addToFavIcon.setOnClickListener {
                if(word.favourite==true){
                    addToFavIcon.setImageDrawable(ContextCompat.getDrawable(itemView.context,R.drawable.ic_baseline_favorite_24))
                    word.favourite = false
                }else{
                    addToFavIcon.setImageDrawable(ContextCompat.getDrawable(itemView.context,R.drawable.ic_baseline_favorite_24_red))
                    word.favourite = true
                }
                wordViewModel.updateCurrentWord(word)
            }
        }
    }

}