package net.kelmer.android.utils.imagefetcher

import android.content.Context
import android.os.Environment
import java.io.File
import kotlin.math.abs

class FileCache(context: Context) {

    private val cacheDir: File = context.cacheDir

    init {
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
    }

    fun getFile(url: String): File {
        val filename = abs(url.hashCode()).toString()
        return File(cacheDir, filename)
    }

    fun clear() {
        val listFiles = cacheDir.listFiles() ?: return
        listFiles.forEach {
            it.delete()
        }
    }

}