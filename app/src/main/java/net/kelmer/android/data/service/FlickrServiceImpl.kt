package net.kelmer.android.data.service

import net.kelmer.android.data.serializer.Serializer
import net.kelmer.android.data.model.ApiResponse
import net.kelmer.android.utils.client.HttpClient


class FlickrServiceImpl(
    private val baseUrl: String,
    private val serializer: Serializer,
    private val client: HttpClient
) : FlickrService {

    override fun getSearch(apiKey: String, term: String): ApiResponse {
        return searchRequest(apiKey, term)
    }

    private fun searchRequest(apiKey: String, term: String): ApiResponse {
        val fullUrl =
            "$baseUrl/services/rest?method=flickr.photos.search&format=json&nojsoncallback=1&privacy_filter=0&api_key=$apiKey&text=$term"
        val response = client.doGet(fullUrl)
        return deserialize(response)
    }

    private fun deserialize(response: String): ApiResponse {
        return serializer.deserialize(response)
    }
}