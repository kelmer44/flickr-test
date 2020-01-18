package net.kelmer.android.data.service

import io.reactivex.Single
import net.kelmer.android.data.model.ApiResponse

class FlickrServiceImpl : FlickrService{
    override fun getSearch(apiKey: String, term: String): Single<ApiResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}