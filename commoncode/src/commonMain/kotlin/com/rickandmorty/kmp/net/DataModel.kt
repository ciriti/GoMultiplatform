package com.rickandmorty.kmp.net

import com.arrow.core.MpTry
import io.ktor.client.response.HttpResponse
import io.ktor.client.response.readText
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Result(
    val id: Int = -1,
    val name: String = "",
    val status: String = "",
    val image: String = ""
)

@Serializable
data class CharactersResponse(
    val results: List<Result> = emptyList()
)

@kotlinx.serialization.UnstableDefault
fun<T> mapper(t : KSerializer<T>): suspend (HttpResponse) -> MpTry<T> =
    { httpResponse : HttpResponse -> httpResponse.parseJson(t)  }

@kotlinx.serialization.UnstableDefault
suspend fun <T> HttpResponse.parseJson(kSerializer: KSerializer<T>): MpTry<T> {
    return  MpTry { Json.nonstrict.parse(kSerializer, readText()) }
}