package net.kelmer.android.network

import java.util.concurrent.Future

class DeferredTaskRunner<T>(private val task: Task<T>) : TaskRunner<T>()  {

    fun execute(callback: Callback<T>): FutureTask<*> {
        return execute(task, callback)
    }
}
