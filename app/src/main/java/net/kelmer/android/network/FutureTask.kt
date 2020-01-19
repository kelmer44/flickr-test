package net.kelmer.android.network

import android.util.Log
import java.util.concurrent.Future

class FutureTask<T>(private val future: Future<T>) {

    init {
        future.
    }
    
    fun cancel(){
        Log.i("SLEEP", "future cancelled: ${future.isCancelled} || is Done: ${future.isDone} || gonna get cancelled")
        future.cancel(true)
    }
}