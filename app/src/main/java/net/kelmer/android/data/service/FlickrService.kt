package net.kelmer.android.data.service

import net.kelmer.android.data.model.ApiResponse

interface FlickrService {
//12f7e02a64502c622306c2a9145997a6

    fun getSearch(
        apiKey: String,
        term: String
    ) : ApiResponse
}
