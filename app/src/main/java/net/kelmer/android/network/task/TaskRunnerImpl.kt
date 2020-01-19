package net.kelmer.android.network.task

import android.os.Handler
import android.os.Looper
import android.util.Log
import java.io.InterruptedIOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TaskRunnerImpl<T>(private val task: Task<T>) : TaskRunner<T> {

    val TAG = TaskRunnerImpl::class.java.simpleName + ".TAG"
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()
    private val handler: Handler = Handler(Looper.getMainLooper())

    override fun execute(callback: Callback<T>): FutureTask<*> {
        return execute(task, callback)
    }

    private fun execute(task: Task<T>, callback: Callback<T>): FutureTask<*> {
        return FutureTask(executor.submit {
            try {
                val result = task.call()
                handler.post {
                    if (Thread.interrupted()) {
                        throw InterruptedIOException("Thread interrupted!")
                    }
                    callback.onResponse(result)
                }
            } catch (e: Exception) {
                if (e is InterruptedIOException) {
                    Log.v(TAG, "Task was cancelled!")
                } else {
                    handler.post {
                        callback.onFailure(e)
                    }
                }
            }
        })
    }

    override fun cancel() {
        Log.v(TAG, "Cancelling now all tasks")
        executor.shutdownNow()
    }

}