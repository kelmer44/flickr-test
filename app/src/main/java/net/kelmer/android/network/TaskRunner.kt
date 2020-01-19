package net.kelmer.android.network

import android.os.Handler
import android.os.Looper
import android.util.Log
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

open class TaskRunner<T> {

    val TAG = TaskRunner::class.java.simpleName + ".TAG"
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()
    private val handler: Handler = Handler(Looper.getMainLooper())


    fun execute(task: Task<T>, callback: Callback<T>): FutureTask<*> {
        return FutureTask(executor.submit {
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
        })
    }

    fun cancel(){
        Log.v(TAG, "Cancelling now all tasks")
        executor.shutdownNow()
    }

}