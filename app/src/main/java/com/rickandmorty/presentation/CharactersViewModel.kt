package com.rickandmorty.presentation

import androidx.lifecycle.MutableLiveData
import com.datalayer.repository.CharactersRepo
import com.datalayer.net.CharactersResponse
import com.datalayer.net.Result
import com.rickandmorty.core.BaseViewModel
import com.rickandmorty.presentation.BaseState.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * Created by Carmelo Iriti
 */

open class CharactersViewModel(
    private val charactersRepo: CharactersRepo,
    dispatcher: CoroutineContext = Dispatchers.Main
) : BaseViewModel(dispatcher){

    val liveData by lazy {
        MutableLiveData<BaseState>()
            .apply { value = StateCharacters(emptyList()) }
    }

    fun fetchCharacters(page : Int){
        launch(job) {
            withContext(Dispatchers.IO) { charactersRepo.getCharacters(page) }.fold(
                ::onError,
                ::onSuccess
            )
        }
    }

    private fun onSuccess(data : CharactersResponse){
        liveData.value = StateCharacters(data.results?: emptyList())
    }

    private fun onError(error : Throwable){
        liveData.value = StateError(error.message?:"error")
    }

}

sealed class BaseState{
    data class StateCharacters(val list: List<Result>) : BaseState()
    data class StateError(val errorMessage: String) : BaseState()
}