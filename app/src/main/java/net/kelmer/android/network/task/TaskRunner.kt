package net.kelmer.android.network.task

import android.os.Handler
import android.os.Looper
import android.util.Log
import java.io.InterruptedIOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

open class TaskRunner<T> {

    val TAG = TaskRunner::class.java.simpleName + ".TAG"
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()
    private val handler: Handler = Handler(Looper.getMainLooper())


    fun execute(task: Task<T>, callback: Callback<T>): FutureTask<*> {
        return FutureTask(executor.submit {
            try {
                val result = task.call()
                handler.post {
                    if(Thread.interrupted()){
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

    fun cancel() {
        Log.v(TAG, "Cancelling now all tasks")
        executor.shutdownNow()
    }

}