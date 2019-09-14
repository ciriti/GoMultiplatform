package com.arrow.core

class ForTry private constructor() { companion object }

typealias TryOf<A> = Kind<ForTry, A>

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
inline fun <A> TryOf<A>.fix(): MpTry<A> = this as MpTry<A>

typealias Predicate<T> = (T) -> Boolean

fun <A> identity(a: A): A = a

/**
 * The `MpTry` type represents a computation that may either result in an exception, or return a
 * successfully computed value.
 *
 * Port of https://github.com/scala/scala/blob/v2.12.1/src/library/scala/util/MpTry.scala
 */
sealed class MpTry<out A> : TryOf<A> {

    companion object {

        fun <A> just(a: A): MpTry<A> = Success(a)

        tailrec fun <A, B> tailRecM(a: A, f: (A) -> TryOf<Either<A, B>>): MpTry<B> {
            val ev: MpTry<Either<A, B>> = f(a).fix()
            return when (ev) {
                is Failure -> Failure(ev.exception).fix()
                is Success -> {
                    val b: Either<A, B> = ev.value
                    when (b) {
                        is Either.Left -> tailRecM(b.a, f)
                        is Either.Right -> Success(b.b)
                    }
                }
            }

        }

        inline operator fun <A> invoke(f: () -> A): MpTry<A> =
                try {
                    Success(f())
                } catch (e: Throwable) {
                    Failure(e)
                }

        fun <A> raise(e: Throwable): MpTry<A> = Failure(e)

    }

    fun <B> ap(ff: TryOf<(A) -> B>): MpTry<B> = ff.fix().flatMap { f -> map(f) }.fix()

    /**
     * Returns the given function applied to the value from this `Success` or returns this if this is a `Failure`.
     */
    inline fun <B> flatMap(f: (A) -> TryOf<B>): MpTry<B> =
            when (this) {
                is Failure -> this
                is Success -> f(value).fix()
            }

    /**
     * Maps the given function to the value from this `Success` or returns this if this is a `Failure`.
     */
    inline fun <B> map(f: (A) -> B): MpTry<B> =
            flatMap { Success(f(it)) }

    /**
     * Converts this to a `Failure` if the predicate is not satisfied.
     */
    fun filter(p: Predicate<A>): MpTry<A> =
            flatMap { if (p(it)) Success(it) else Failure(TryException.PredicateException("Predicate does not hold for $it")) }

//    fun <B> mapFilter(f: (A) -> Option<B>): MpTry<B> =
//            flatMap { a -> f(a).fold({ Failure(TryException.PredicateException("Predicate does not hold")) }, { MpTry.just(it) }) }

    /**
     * Inverts this `MpTry`. If this is a `Failure`, returns its exception wrapped in a `Success`.
     * If this is a `Success`, returns a `Failure` containing an `UnsupportedOperationException`.
     */
    fun failed(): MpTry<Throwable> =
            fold(
                    { Success(it) },
                    { Failure(TryException.UnsupportedOperationException("Success")) }
            )

    /**
     * Applies `ifFailure` if this is a `Failure` or `ifSuccess` if this is a `Success`.
     */
    inline fun <B> fold(ifFailure: (Throwable) -> B, ifSuccess: (A) -> B): B =
            when (this) {
                is Failure -> ifFailure(exception)
                is Success -> ifSuccess(value)
            }

    abstract fun isFailure(): Boolean

    abstract fun isSuccess(): Boolean

    fun exists(predicate: Predicate<A>): Boolean = fold({ false }, { predicate(it) })

//    fun toOption(): Option<A> = fold({ None }, { Some(it) })
//
    fun toEither(): Either<Throwable, A> = fold({ Left(it) }, { Right(it) })

    /**
     * * Convenient method to solve a common scenario when using [MpTry]. The created [MpTry] object is often
     * converted to [Either], and right after [Either.mapLeft] is called to translate the [Throwable] to a
     * domain specific error object.
     * * To make it easier this method takes an [onLeft] error domain object supplier, which does the conversion to domain error
     * in the same time as conversion to [Either] occurs.
     * * So instead of
     * ```
     * MpTry {
     *    dangerousOperation()
     * }.toEither()
     *    .mapLeft { Error.ServerError("This really went wrong", it) }
     * // Left(a=Error.ServerError@3ada9e34)
     * ```
     * One can write
     * ```
     * MpTry {
     *    dangerousOperation()
     * }.toEither {
     *    Error.ServerError("This really went wrong", it)
     * }
     * // Left(a=Error.ServerError@4a5a3234)
     * ```
     */
    fun <B> toEither(onLeft: (Throwable) -> B): Either<B, A> = this.toEither().fold({ onLeft(it).left() }, { it.right() })
//
    fun <B> foldLeft(initial: B, operation: (B, A) -> B): B = fix().fold({ initial }, { operation(initial, it) })
//
    fun <B> foldRight(initial: Eval<B>, operation: (A, Eval<B>) -> Eval<B>): Eval<B> = fix().fold({ initial }, { operation(it, initial) })

    /**
     * The `Failure` type represents a computation that result in an exception.
     */
    data class Failure(val exception: Throwable) : MpTry<Nothing>() {
        override fun isFailure(): Boolean = true

        override fun isSuccess(): Boolean = false
    }

    /**
     * The `Success` type represents a computation that return a successfully computed value.
     */
    data class Success<out A>(val value: A) : MpTry<A>() {
        override fun isFailure(): Boolean = false

        override fun isSuccess(): Boolean = true
    }
}

sealed class TryException(override val message: String) : Exception(message) {
    data class PredicateException(override val message: String) : TryException(message)
    data class UnsupportedOperationException(override val message: String) : TryException(message)
}

/**
 * Returns the value from this `Success` or the given `default` argument if this is a `Failure`.
 *
 * ''Note:'': This will throw an exception if it is not a success and default throws an exception.
 */
inline fun <B> TryOf<B>.getOrDefault(default: () -> B): B = fix().fold({ default() }, ::identity)

/**
 * Returns the value from this `Success` or the given `default` argument if this is a `Failure`.
 *
 * ''Note:'': This will throw an exception if it is not a success and default throws an exception.
 */
inline fun <B> TryOf<B>.getOrElse(default: (Throwable) -> B): B = fix().fold(default, ::identity)

/**
 * Returns the value from this `Success` or null if this is a `Failure`.
 */
fun <B> TryOf<B>.orNull(): B? = getOrElse { null }

inline fun <B, A : B> TryOf<A>.orElse(f: () -> TryOf<B>): MpTry<B> = when (fix()) {
    is MpTry.Success -> fix()
    is MpTry.Failure -> f().fix()
}

/**
 * Applies the given function `f` if this is a `Failure`, otherwise returns this if this is a `Success`.
 * This is like `flatMap` for the exception.
 */
fun <B> TryOf<B>.recoverWith(f: (Throwable) -> TryOf<B>): MpTry<B> = fix().fold({ f(it).fix() }, { MpTry.Success(it) })

fun <A> TryOf<A>.rescue(f: (Throwable) -> TryOf<A>): MpTry<A> = fix().recoverWith(f)

/**
 * Applies the given function `f` if this is a `Failure`, otherwise returns this if this is a `Success`.
 * This is like map for the exception.
 */
fun <B> TryOf<B>.recover(f: (Throwable) -> B): MpTry<B> = fix().fold({ MpTry.Success(f(it)) }, { MpTry.Success(it) })

fun <A> (() -> A).try_(): MpTry<A> = MpTry(this)

fun <A> A.success(): MpTry<A> = MpTry.Success(this)

fun <A> Throwable.failure(): MpTry<A> = MpTry.Failure(this)

fun <T> TryOf<TryOf<T>>.flatten(): MpTry<T> = fix().flatMap(::identity)