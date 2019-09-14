package com.datalayer.repository

import arrow.core.Try
import com.datalayer.net.CharactersResponse
import com.datalayer.util.Logger
import com.datalayer.net.NetworkClient
import java.lang.RuntimeException

interface CharactersRepo {
    suspend fun getCharacters(page: Int): Try<CharactersResponse>
    companion object
}

fun CharactersRepo.Companion.create(networkClient : NetworkClient, logger : Logger) : CharactersRepo =
    CharactersRepoImpl(networkClient, logger)

private class CharactersRepoImpl(val api : NetworkClient, val logger : Logger) :
    CharactersRepo {

    override suspend fun getCharacters(page: Int): Try<CharactersResponse> {

        val response = api.fetchCharacters(page).await()
        return when(response.isSuccessful){
            true -> Try{ response.body()!! }.also { logger.info("$it") }
            false -> Try.Failure(RuntimeException("Something went wrong"))
        }
    }
}