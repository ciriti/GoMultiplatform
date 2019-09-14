package com.rickandmorty.kmp

import io.ktor.client.engine.HttpClientEngine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

internal expect val applicationDispatcher: CoroutineDispatcher

internal expect val clientEngine : HttpClientEngine

interface Logger{
    fun log(tag : String, message : String)
}

// expected classes do not have default constructor, we need to specify
internal expect class LoggerImpl() : Logger{
    override fun log(tag : String, message : String)
}

/**
 * Defines a scope for new coroutines on ios
 */
internal class MainScope: CoroutineScope {
    private val dispatcher = applicationDispatcher
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = dispatcher + job
}


expect fun expectedTimestamp() : Long