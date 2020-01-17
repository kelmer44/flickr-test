package net.kelmer.android.common

/**
 * Created by gabriel on 06/01/2020.
 */
sealed class Resource<T>(val inProgress: Boolean) {

    class InProgress<T> : Resource<T>(true) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            return true
        }

        override fun hashCode(): Int = javaClass.hashCode()
    }

    data class Success<T>(val data: T) : Resource<T>(false)
    abstract class Failure<T>(val errorMessage: String = "", val e: Throwable) : Resource<T>(false)
    data class GenericFailure<T>(val m: String = "Generic Error", val t: Throwable) :
        Failure<T>(m, t)

    companion object {
        fun <T> inProgress(): Resource<T> = InProgress()
        fun <T> success(data: T): Resource<T> = Success(data)
        fun <T> failure(e: Throwable): Resource<T> = GenericFailure(e.message ?: "unknown", t = e)
    }
}

fun <T> Resource<T>.resolve(onError: (E: Throwable) -> Unit = {}, onSuccess: (T) -> Unit = {}) {
    when (this) {
        is Resource.Success -> {
            onSuccess(data)
        }
        is Resource.Failure -> {
            onError(e)
        }
    }
}
