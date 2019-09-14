package com.rickandmorty.kmp

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.ios.Ios
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import platform.Foundation.NSDate
import platform.Foundation.NSThread
import platform.Foundation.timeIntervalSince1970
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import kotlin.coroutines.CoroutineContext

/**
 * Client HTTP for ios
 */
actual val clientEngine : HttpClientEngine = Ios.create()

/**
 * dispatcher implementation
 */
internal actual val applicationDispatcher: CoroutineDispatcher = IosDispatcher()

/**
 *  dispatcher implementations for ios
 */
private class IosDispatcher: CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatch_get_main_queue()) { block.run() }
    }
}

internal actual class LoggerImpl : Logger{
    actual override fun log(tag : String, message : String){
        // using NSLog
        expectedTimestamp()
    }
}

actual fun expectedTimestamp() : Long = NSDate().timeIntervalSince1970.toLong()