package net.kelmer.android.network


interface Callback<T> {

    fun onResponse(data: T)
    fun onFailure(t: Throwable)
}