package com.rickandmorty.kmp

import com.arrow.core.MpTry
import com.rickandmorty.kmp.net.CharactersResponse
import com.rickandmorty.kmp.repository.CharactersRepoMp
import com.rickandmorty.kmp.repository.create
import com.rickandmorty.kmp.usecase.UseCaseMp
import com.rickandmorty.kmp.usecase.create
import io.mockk.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ApiMpTest {

    @Test
    fun api_test() = runBlocking {
        val api = NetworkClientMp.create()
        val model = api.fetchCharacters(1)
        println(model)
        Unit
    }

    @Test
    fun datasource_test() = runBlocking {
        val ds = CharactersRepoMp.create()
        val model = ds.getCharacters(1)
        println(model)
        Unit
    }

    @Test
    fun useCase_test_1() = runBlocking {
        val mock = mockk<CharactersRepoMp>()
        val mockSuccess = mockk<(CharactersResponse) -> Unit>()
        val mockFailure = mockk<(Throwable) -> Unit>()
        val res = CharactersResponse()
        every { mockSuccess(any()) } answers {}
        coEvery { mock.getCharacters(1) } returns MpTry{ res }
        val uc : UseCaseMp = UseCaseMp.create(ds = mock)
        val job = uc.getCharacters(
            page = 1,
            success = mockSuccess,
            failure = mockFailure)

        delay(2000)

        verify(exactly = 1) { mockSuccess(res) }
        verify(exactly = 0) { mockFailure(any()) }

        Unit
    }

    @Test
    fun useCase_test_1_failure() = runBlocking {
        val mock = mockk<CharactersRepoMp>()
        val mockSuccess = mockk<(CharactersResponse) -> Unit>()
        val mockFailure = mockk<(Throwable) -> Unit>()
        every { mockFailure(any()) } answers {}
        coEvery { mock.getCharacters(1) } returns MpTry.raise(RuntimeException())
        val uc : UseCaseMp = UseCaseMp.create(ds = mock)
        val model = uc.getCharacters(
            page = 1,
            success = mockSuccess,
            failure = mockFailure)

        delay(2000)

        verify(exactly = 0) { mockSuccess(any()) }
        verify(exactly = 1) { mockFailure(any()) }

        Unit
    }

}