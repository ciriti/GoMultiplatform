package com.rickandmorty.core

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.rickandmorty.kmp.net.Result
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.row_character.view.*

/**
 * Created by Carmelo Iriti
 */

class RowCharacter @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr)

fun RowCharacter.bind(character: Result) {
  character.image?.let { characterWall.loadFromUrl(it) }
  tv_name.text = character.name
  tv_status.text = character.status
}

fun ImageView.loadFromUrl(url: String) =
  Glide.with(this.context.applicationContext)
    .load(url)
    .transition(DrawableTransitionOptions.withCrossFade())
    .into(this)!!