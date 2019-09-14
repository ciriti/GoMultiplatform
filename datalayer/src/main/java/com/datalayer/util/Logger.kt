package com.datalayer.util

interface Logger {

    fun info(message : String)
    companion object
}

fun Logger.Companion.create() : Logger =
    LoggerImpl()

private class LoggerImpl : Logger {
    override fun info(message: String) {
        // your logger system
    }
}