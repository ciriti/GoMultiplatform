package com.rickandmorty.kmp.repository

import com.arrow.core.MpTry
import com.rickandmorty.kmp.*
import com.rickandmorty.kmp.LoggerImpl
import com.rickandmorty.kmp.net.CharactersResponse
import com.rickandmorty.kmp.net.NetworkClientMp
import com.rickandmorty.kmp.net.create
import kotlinx.coroutines.coroutineScope

interface CharactersRepoMp {
    suspend fun getCharacters(page: Int): MpTry<CharactersResponse>
    companion object
}

/**
 * after compilation for Ios the optional parameters are not taken in consideration
 * you have to set client side
 */
fun createCharactersRepoMpWithOptional(logger : Logger = LoggerImpl()) : CharactersRepoMp =
    CharactersRepoMpImpl(logger = logger)

fun CharactersRepoMp.Companion.create() : CharactersRepoMp = CharactersRepoMpImpl()
fun CharactersRepoMp.Companion.create(logger : Logger) : CharactersRepoMp =
    CharactersRepoMpImpl(logger = logger)
fun createCharactersRepoMp() : CharactersRepoMp = CharactersRepoMpImpl()

private class CharactersRepoMpImpl(
    val logger : Logger = LoggerImpl(),
    val api : NetworkClientMp = NetworkClientMp.create()
) : CharactersRepoMp {

    override suspend fun getCharacters(page: Int): MpTry<CharactersResponse> = coroutineScope{
        api.fetchCharacters(page)
            .apply {
                logger.log(tag = "CharactersRepo", message = "")
            }
    }
}