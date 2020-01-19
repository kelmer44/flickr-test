package net.kelmer.android.network.imagefetcher

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import androidx.annotation.DrawableRes
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.util.Collections
import java.util.WeakHashMap
import java.util.concurrent.Executors
import net.kelmer.android.flickrsearch.R

class ImageFetcher(context: Context) {
    companion object {
        val TAG = ImageFetcher::class.java.simpleName
    }

    private val fileCache = FileCache(context)
    private val imageViews = Collections.synchronizedMap(WeakHashMap<ImageView, String>())
    private val executorService = Executors.newFixedThreadPool(5)

    fun load(url: String, imageView: ImageView, @DrawableRes loader: Int) {
        imageViews[imageView] = url
        queuePhoto(url, imageView)
        imageView.setImageResource(loader)
    }

    private fun queuePhoto(url: String, imageView: ImageView) {
        executorService.submit(ImageLoader(ViewAndUrl(url, imageView)))
    }

    private fun getBitmap(url: String, width: Int, height: Int): Bitmap? {
        val f = fileCache.getFile(url)

        val b = decodeFile(f, width, height)
        if (b != null) {
            return b
        }

        return try {
            val imageUrl = URL(url)
            val connection = imageUrl.openConnection() as HttpURLConnection
            connection.instanceFollowRedirects = true
            val inputStream = connection.inputStream
            Log.i(TAG, "Opening Output Stream for ${f.name}")
            val outputStream = FileOutputStream(f)
            copyStream(inputStream, outputStream)
            outputStream.close()
            decodeFile(f, width, height)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting bitmap", e)
            null
        }
    }

    private fun decodeFile(f: File, ivWidth: Int, ivHeight: Int): Bitmap? {
        try {

            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            Log.i(TAG, "Decoding bitmap for ${f.name}")
            BitmapFactory.decodeStream(FileInputStream(f), null, options)

            // Width and height of the bitmap
            val bmpWidth = options.outWidth
            val bmpHeight = options.outHeight

            val requiredSize = ivWidth.coerceAtMost(bmpWidth)
            var widthTmp = bmpWidth
            Log.w(TAG, "For image ${f.name} bmp size is ($bmpWidth, $bmpHeight) and iv size is $ivWidth,$ivHeight, resulting size will be $requiredSize }")
            var heightTmp = bmpHeight
            var scale = 1
            while (true) {
                if ((widthTmp / 2 < requiredSize) || heightTmp / 2 < requiredSize)
                    break
                widthTmp /= 2
                heightTmp /= 2
                scale *= 2
            }

            // decode
            val decodeOptions = BitmapFactory.Options()
            Log.w(TAG, "Scale size will be $scale")
            decodeOptions.inSampleSize = scale
            return BitmapFactory.decodeStream(FileInputStream(f), null, decodeOptions)
        } catch (e: Exception) {
            Log.e(TAG, "Error decoding bitmap", e)
        }
        return null
    }

    private fun copyStream(istream: InputStream, ostream: OutputStream) {
        val bufferSize = 1024
        try {
            val bytes = ByteArray(bufferSize)
            while (true) {
                val count = istream.read(bytes, 0, bufferSize)
                if (count == -1)
                    break
                ostream.write(bytes, 0, count)
            }
        } catch (e: Exception) {

            Log.e(TAG, "Error copying stream", e)
        }
    }

    inner class ImageLoader(private val image: ViewAndUrl) : Runnable {
        override fun run() {
            if (imageViewReused(image)) {
                return
            }
            val bmp = getBitmap(image.url, image.imageView.width, image.imageView.height)
            val displayer = BitmapDisplayer(bmp, image)
            (image.imageView.context as Activity).runOnUiThread(displayer)
        }
    }

    private fun imageViewReused(image: ViewAndUrl): Boolean {
        val tag = imageViews[image.imageView]
        return tag == null || tag != image.url
    }

    inner class BitmapDisplayer(private val b: Bitmap?, private val image: ViewAndUrl) : Runnable {
        override fun run() {
            if (imageViewReused(image)) {
                return
            }
            if (b != null) {
                image.imageView.setImageBitmap(b)
            } else {
                image.imageView.setImageResource(R.drawable.ic_image_error)
            }
        }
    }

    data class ViewAndUrl(val url: String, val imageView: ImageView)
}
