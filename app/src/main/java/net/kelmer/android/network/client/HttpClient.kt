package net.kelmer.android.network.client

interface HttpClient {

    fun doGet(serviceUrl: String): String

}
