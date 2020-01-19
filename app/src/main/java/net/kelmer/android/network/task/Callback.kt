package net.kelmer.android.network.task


interface Callback<T> {

    fun onResponse(data: T)
    fun onFailure(t: Throwable)
}