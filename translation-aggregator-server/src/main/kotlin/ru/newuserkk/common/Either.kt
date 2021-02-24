package ru.newuserkk.common

typealias OperationResult<T> = Either<Exception, T>

sealed class Either<out A, out B>

data class Left<A>(val value: A) : Either<A, Nothing>()
data class Right<B>(val value: B) : Either<Nothing, B>()

fun <A> leftOf(value: A): Left<A> = Left(value)
fun <B> rightOf(value: B): Right<B> = Right(value)
fun <B> eitherOf(value: B): Right<B> = rightOf(value)

fun <A> A.asLeft(): Left<A> = leftOf(this)
fun <B> B.asRight(): Right<B> = rightOf(this)
fun <B> B.asEither() = asRight()

fun <T> runSafely(block: () -> T): OperationResult<T> = try {
    block().asRight()
} catch (e: Exception) {
    e.asLeft()
}

suspend fun <T> runSafelySuspended(block: suspend () -> T): OperationResult<T> = try {
    block().asRight()
} catch (e: Exception) {
    e.asLeft()
}
