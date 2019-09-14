package com.datalayer

import arrow.core.Try
import java.lang.RuntimeException

fun CharactersDataSource.Companion.create(api : Api, logger : Logger) : CharactersDataSource =
    CharactersDataSourceImpl(api, logger)

private class CharactersDataSourceImpl(val api : Api, val logger : Logger) : CharactersDataSource {

    override suspend fun getCharacters(page: Int): Try<CharactersResponse> {

        val response = api.getCharacters(page).await()
        return when(response.isSuccessful){
            true -> Try{ response.body()!! }.also { logger.info("$it") }
            false -> Try.Failure(RuntimeException("Somethikng went wrong"))
        }
    }
}