package net.kelmer.android.network

interface Task<T> {

    fun call() : T
}