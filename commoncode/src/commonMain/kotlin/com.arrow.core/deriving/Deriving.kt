@file:Suppress("NotImplementedDeclaration")
package com.arrow.core.deriving

import com.arrow.core.Either

@Suppress("DEPRECATION", "UNUSED_PARAMETER")
data class Deriving<A>(val value: A) : DerivingOf<A> {
  fun <B> map(f: (A) -> B): Deriving<B> = TODO()

  companion object {
    fun <A> just(a: A): Deriving<A> = TODO()
    fun <A, B> tailRecM(a: A, f: (A) -> DerivingOf<Either<A, B>>): Deriving<B> = TODO()
  }
}
