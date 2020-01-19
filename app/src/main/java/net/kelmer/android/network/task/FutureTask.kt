package net.kelmer.android.network.task

import android.util.Log
import java.util.concurrent.Future

class FutureTask<T>(private val future: Future<T>) {

    fun cancel() {
        future.cancel(true)
    }
}
