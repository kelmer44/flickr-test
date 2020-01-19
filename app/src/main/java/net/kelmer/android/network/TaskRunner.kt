package net.kelmer.android.network

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class TaskRunner<T> {
    private val executor: Executor = Executors.newSingleThreadExecutor()
    private val handler: Handler = Handler(Looper.getMainLooper())


    fun execute(task: Task<T>, callback: Callback<T>){
        executor.execute {
            try {
                val result = task.call()
                handler.post {
                    callback.onResponse(result)
                }
            }
            catch (e: Exception){
                handler.post {
                    callback.onFailure(e)
                }
            }
        }
    }

}