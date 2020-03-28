package com.rickandmorty.presentation

import androidx.lifecycle.MutableLiveData
import com.rickandmorty.core.BaseViewModel
import com.rickandmorty.kmp.usecase.UseCaseMp
import com.rickandmorty.kmp.net.CharactersResponse
import com.rickandmorty.presentation.BaseState.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import com.rickandmorty.kmp.net.Result

/**
 * Created by Carmelo Iriti
 */

open class CharactersViewModel(
    private val useCaseMp: UseCaseMp,
    dispatcher: CoroutineContext = Dispatchers.Main
) : BaseViewModel(dispatcher){

    val liveData by lazy {
        MutableLiveData<BaseState>()
            .apply { value = StateCharacters(emptyList()) }
    }

    fun fetchCharactersMp(page : Int){
        launch(job) {
            println("thrname: ${Thread.currentThread().name}")
            withContext(Dispatchers.IO) { useCaseMp.getCharacters(page) }.fold(
                ::onError,
                ::onSuccessMp
            )
        }
    }

    private fun onSuccessMp(data : CharactersResponse){
        val list = data.results.map { it.toResponse() }
        liveData.value = StateCharacters(list)
    }

    private fun onError(error : Throwable){
        liveData.value = StateError(error.message?:"error")
    }

}

fun Result.toResponse() = Result(
    id = id,
    image = image,
    name = name,
    status = status
)

sealed class BaseState{
    data class StateCharacters(val list: List<Result>) : BaseState()
    data class StateError(val errorMessage: String) : BaseState()
}