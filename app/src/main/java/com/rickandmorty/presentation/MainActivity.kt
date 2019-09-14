package com.rickandmorty.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rickandmorty.core.init
import com.rickandmorty.R

/**
 * Created by Carmelo Iriti
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        savedInstanceState ?: init(CharactersListFragment(), R.id.fragmentContainer)
    }

}
