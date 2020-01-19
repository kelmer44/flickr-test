package net.kelmer.android.network.task

interface Task<T> {

    fun call() : T
}