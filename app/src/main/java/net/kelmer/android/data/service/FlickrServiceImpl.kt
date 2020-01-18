package net.kelmer.android.data.service

import io.reactivex.Single
import net.kelmer.android.data.Serializer
import net.kelmer.android.data.model.ApiResponse
import net.kelmer.android.utils.HttpClient
import java.lang.Exception


class FlickrServiceImpl(private val baseUrl: String, private val serializer: Serializer, private val client: HttpClient) : FlickrService {

    override fun getSearch(apiKey: String, term: String): Single<ApiResponse> {
        return Single.fromCallable {
            searchRequest(apiKey, term)
        }

    }

    private fun searchRequest(apiKey: String, term: String): ApiResponse {
        val fullUrl =
            "$baseUrl/services/rest?method=flickr.photos.search&format=json&nojsoncallback=1&api_key=$apiKey&text=$term"
        val response = client.doGet(fullUrl)
        return deserialize(response)
    }

    private fun deserialize(response: String): ApiResponse {
        return serializer.deserialize(response)
    }
}