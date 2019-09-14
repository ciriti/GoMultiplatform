package com.rickandmorty.kmp

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal actual val applicationDispatcher: CoroutineDispatcher = Dispatchers.Default

internal actual val clientEngine : HttpClientEngine = OkHttp.create()

internal actual class LoggerImpl : Logger{
    actual override fun log(tag : String, message : String){
        // your favorite logger
    }
}

actual fun expectedTimestamp() : Long = System.currentTimeMillis()