package net.kelmer.android.network.task

import android.util.Log
import java.util.concurrent.Future

class FutureTask<T>(private val future: Future<T>) {

    fun cancel() {
        Log.v("RESULTTEST", "future cancelled: ${future.isCancelled} || is Done: ${future.isDone} || gonna get cancelled")
        future.cancel(true)
    }
}
