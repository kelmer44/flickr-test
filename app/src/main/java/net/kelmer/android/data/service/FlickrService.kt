package net.kelmer.android.data.service

import io.reactivex.Single
import net.kelmer.android.data.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FlickrService {
//12f7e02a64502c622306c2a9145997a6

    @GET("services/rest?method=flickr.photos.search&format=json&nojsoncallback=1")
    fun getSearch(
        @Query("api_key") apiKey: String, @Query("text") term: String
    ) : Single<ApiResponse>
}
