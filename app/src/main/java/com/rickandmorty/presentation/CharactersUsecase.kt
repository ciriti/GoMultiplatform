package com.rickandmorty.presentation

import arrow.core.Try
import com.datalayer.repository.CharactersRepo
import com.datalayer.net.CharactersResponse
import kotlinx.coroutines.coroutineScope

interface CharactersUsecase {
    suspend fun getCharacters(page: Int): Try<CharactersResponse>
    companion object
}

fun CharactersUsecase.Companion.create(charactersRepo: CharactersRepo): CharactersUsecase =
    CharactersUsecaseImpl(charactersRepo)

private class CharactersUsecaseImpl(
    private val charactersRepo: CharactersRepo
) : CharactersUsecase {

    override suspend fun getCharacters(page: Int): Try<CharactersResponse> = coroutineScope {
        charactersRepo.getCharacters(page)
            .apply {
                /**
                 * you can combine with other objects
                 * you can execute other actions
                 */
            }
    }
}
