package net.kelmer.android.utils

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class StringResponseHttpClient : HttpClient {
    override fun doGet(serviceUrl: String) : String? {

        var reader: BufferedReader? = null
        try {
            val url = URL(serviceUrl)
            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "GET"
            connection.connect()

            val responseCode = connection.responseCode
            if (responseCode != 200) {
                return null
            }

            val inputStream = connection.inputStream ?: return null
            reader = BufferedReader(InputStreamReader(inputStream))


            val readLines = reader.readLines()

            if (readLines.isEmpty()) {
                return null
            }

            return readLines.joinToString("\n")
        }
        catch (e: IOException){
            e.printStackTrace()
            return null
        }
        finally {
            if(reader!=null){
                try{
                    reader.close()
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }
}