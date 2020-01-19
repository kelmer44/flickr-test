package net.kelmer.android.data.service

import net.kelmer.android.data.model.ApiResponse

interface FlickrService {

    fun search(
        apiKey: String,
        term: String,
        perPage: Int,
        page: Int
    ): ApiResponse
}
