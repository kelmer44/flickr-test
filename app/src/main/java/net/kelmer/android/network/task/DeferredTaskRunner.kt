package net.kelmer.android.network.task

class DeferredTaskRunner<T>(private val task: Task<T>) : TaskRunner<T>()  {

    fun execute(callback: Callback<T>): FutureTask<*> {
        return execute(task, callback)
    }
}
