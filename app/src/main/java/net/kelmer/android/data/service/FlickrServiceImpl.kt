package net.kelmer.android.data.service

import android.util.Log
import net.kelmer.android.data.serializer.Serializer
import net.kelmer.android.data.model.ApiResponse
import net.kelmer.android.network.client.HttpClient
import kotlin.random.Random


class FlickrServiceImpl(
    private val baseUrl: String,
    private val serializer: Serializer,
    private val client: HttpClient
) : FlickrService {

    override fun search(apiKey: String, term: String, perPage:Int, page: Int): ApiResponse {
        return searchRequest(apiKey, term, perPage, page)
    }

    private fun searchRequest(
        apiKey: String,
        term: String,
        perPage: Int,
        page: Int
    ): ApiResponse {
        val fullUrl =
            "$baseUrl/services/rest?method=flickr.photos.search&format=json&nojsoncallback=1&privacy_filter=0&api_key=$apiKey&text=$term&per_page=$perPage&page=$page"
        val response = client.doGet(fullUrl)
        return deserialize(response)
    }

    private fun deserialize(response: String): ApiResponse {
        return serializer.deserialize(response)
    }
}