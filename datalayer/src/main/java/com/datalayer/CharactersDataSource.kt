package com.datalayer

import arrow.core.Try

interface CharactersDataSource {
    suspend fun getCharacters(page: Int): Try<CharactersResponse>
    companion object
}