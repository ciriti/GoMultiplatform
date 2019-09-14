package com.arrow.core.deriving

import com.arrow.core.Kind

class ForDeriving private constructor() { companion object }
typealias DerivingOf<A> = Kind<ForDeriving, A>

fun <A> DerivingOf<A>.fix(): Deriving<A> = this as Deriving<A>
