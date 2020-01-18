package net.kelmer.android.utils

interface HttpClient {

    fun doGet(serviceUrl: String): String
}