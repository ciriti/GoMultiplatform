package com.rickandmorty.kmp

import com.arrow.core.MpTry
import com.rickandmorty.kmp.net.CharactersResponse
import com.rickandmorty.kmp.repository.CharactersRepoMp
import com.rickandmorty.kmp.repository.create
import kotlinx.coroutines.*

interface UseCaseMp {
    fun getCharacters(
        page: Int,
        success: (CharactersResponse) -> Unit,
        failure: (Throwable) -> Unit
    )

    fun cancelChildren()

    suspend fun getCharacters(page: Int): MpTry<CharactersResponse>

    companion object
}

fun UseCaseMp.Companion.create(ds: CharactersRepoMp = CharactersRepoMp.create()): UseCaseMp =
    UseCaseMpImpl(ds)

fun UseCaseMp.Companion.create(): UseCaseMp = UseCaseMpImpl(CharactersRepoMp.create())

fun createUseCaseMp(): UseCaseMp = UseCaseMpImpl(CharactersRepoMp.create())

fun createUseCaseMp(ds: CharactersRepoMp): UseCaseMp = UseCaseMpImpl(ds = ds)

private class UseCaseMpImpl(
    val ds: CharactersRepoMp = CharactersRepoMp.create()
) : UseCaseMp {

    val scope by lazy { MainScope() }

    override suspend fun getCharacters(page: Int): MpTry<CharactersResponse> = coroutineScope {
        ds.getCharacters(page)
            .apply {
                /**
                 * you can combine with other objects
                 * you can execute other actions
                 */
            }
    }

    override fun getCharacters(
        page: Int,
        success: (CharactersResponse) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        scope.launch {
            when (val res = ds.getCharacters(page)) {
                is MpTry.Success<CharactersResponse> -> success(res.value)
                is MpTry.Failure -> failure(res.exception)
            }
        }
    }

    override fun cancelChildren() {
        scope.coroutineContext.cancelChildren()
    }
}