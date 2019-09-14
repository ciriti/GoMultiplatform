package com.rickandmorty.kmp.net

import com.arrow.core.MpTry
import com.rickandmorty.kmp.clientEngine
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.response.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json

/**
 * Client definition
 */
interface NetworkClientMp {
    companion object{
        const val BASE_URL_SERVICE = "rickandmortyapi.com/api"
    }
    suspend fun fetchCharacters(page: Int): MpTry<CharactersResponse>
}

/**
 * Factory methods
 */
fun NetworkClientMp.Companion.create() : NetworkClientMp = NetworkClientMpImpl()
fun NetworkClientMp.Companion.create(clientHttpEngine: HttpClientEngine) : NetworkClientMp =
    NetworkClientMpImpl(clientHttpEngine = clientHttpEngine)

fun createApi() : NetworkClientMp = NetworkClientMpImpl()
fun createApi(clientHttpEngine: HttpClientEngine) : NetworkClientMp =
    NetworkClientMpImpl(clientHttpEngine = clientHttpEngine)

/**
 * Implementation
 */
private class NetworkClientMpImpl(
    clientHttpEngine: HttpClientEngine = clientEngine,
    val mapper : suspend (HttpResponse) -> MpTry<CharactersResponse> = mapper(CharactersResponse.serializer())
) : NetworkClientMp {

    private val client: HttpClient = HttpClient(clientHttpEngine) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(Json.nonstrict).apply {
                setMapper(CharactersResponse::class, CharactersResponse.serializer())
            }
        }
    }

    override suspend fun fetchCharacters(page: Int): MpTry<CharactersResponse> = coroutineScope {
        val response = client.get<HttpResponse> {
            url {
                protocol = URLProtocol.HTTPS
                host = NetworkClientMp.BASE_URL_SERVICE
                encodedPath = "character/?page=$page"
            }
        }
        when (response.status.value) {
            HttpStatusCode.OK.value -> {
                mapper(response)
            }
            else -> {
                throw RuntimeException("${response.status.value} ${response.status.description}")
            }
        }
    }
}