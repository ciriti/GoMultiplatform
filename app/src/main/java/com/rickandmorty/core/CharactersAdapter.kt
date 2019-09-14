package com.rickandmorty.core

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rickandmorty.R
import kotlin.properties.Delegates
import com.rickandmorty.kmp.net.Result

/**
 * Created by Carmelo Iriti
 */

class CharactersAdapter : RecyclerView.Adapter<CharactersAdapter.ViewHolder>() {

    var list : List<Result> by Delegates.observable(mutableListOf()){
        _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
       return ViewHolder(parent.inflate(R.layout.row_character))
    }


    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.bind(list[position])
    }

    /**
     * Holder class
     */
    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun bind(character: Result) {
            if (itemView is RowCharacter) {
                itemView.bind(character)
            }
        }
    }
}