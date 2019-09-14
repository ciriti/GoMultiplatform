package com.rickandmorty.kmp

import kotlin.native.concurrent.ThreadLocal

// Uncaught Kotlin exception: kotlin.native.concurrent.InvalidMutabilityException:
// mutation attempt of frozen kotlinx.atomicfu.AtomicRef@a6ef28
@ThreadLocal
object ServiceLocator{
    val uc = UseCaseMp.create()
}