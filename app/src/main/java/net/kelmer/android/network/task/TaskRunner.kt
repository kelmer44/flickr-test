package net.kelmer.android.network.task

interface TaskRunner<T>{
    fun execute(callback: Callback<T>): FutureTask<*>
    fun cancel()

}