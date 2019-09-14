package com.rickandmorty.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.rickandmorty.R
import com.rickandmorty.core.CharactersAdapter
import com.rickandmorty.core.configureLayoutManager
import kotlinx.android.synthetic.main.layout_list_characters.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by Carmelo Iriti
 */

class CharactersListFragment : Fragment() {

    val viewModel : CharactersViewModel by viewModel()
    val adapter by lazy { CharactersAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_list_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        charactersList.configureLayoutManager()
        charactersList.adapter = adapter
        viewModel.liveData.observe(this, Observer{ resultHandler(it) })
        viewModel.fetchCharacters(1)
    }

    private fun resultHandler(state : BaseState){
        when(state){
            is BaseState.StateCharacters -> adapter.list = state.list
            is BaseState.StateError -> Toast.makeText(context, state.errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}