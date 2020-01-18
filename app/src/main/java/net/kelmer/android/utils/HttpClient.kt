package net.kelmer.android.utils

interface HttpClient {

    fun doGet(url: String): String?
}