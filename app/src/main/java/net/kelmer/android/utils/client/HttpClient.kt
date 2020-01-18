package net.kelmer.android.utils.client

interface HttpClient {

    fun doGet(serviceUrl: String): String
}