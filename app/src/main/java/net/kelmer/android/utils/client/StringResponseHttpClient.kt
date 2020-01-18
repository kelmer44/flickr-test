package net.kelmer.android.utils.client

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class StringResponseHttpClient : HttpClient {
    override fun doGet(serviceUrl: String) : String {

        var reader: BufferedReader? = null
        try {
            val url = URL(serviceUrl)
            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "GET"
            connection.connect()

            val responseCode = connection.responseCode
            if (responseCode != 200) {
                throw HttpException(
                    responseCode,
                    "Http request error"
                )
            }

            val inputStream = connection.inputStream ?: throw HttpException(
                responseCode,
                "Error executing request"
            )
            reader = BufferedReader(InputStreamReader(inputStream))


            val readLines = reader.readLines()
            if (readLines.isEmpty()) {
                return ""
            }

            return readLines.joinToString("\n")
        }
        catch (e: IOException){
            e.printStackTrace()
            throw e
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