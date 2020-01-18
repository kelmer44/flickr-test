package net.kelmer.android.data.repository

import android.os.AsyncTask
import net.kelmer.android.data.service.FlickrService
import net.kelmer.android.domain.Photo
import net.kelmer.android.domain.PhotoAdapter
import net.kelmer.android.network.Callback
import java.lang.Exception

class PhotoRepositoryImpl(
    private val flickrService: FlickrService,
    private val apiKey: String
) : PhotoRepository {

    private val adapter = PhotoAdapter()
    override fun search(term: String, callback: Callback<List<Photo>>){
        AsyncTask.execute {
            try {
                val apiResponse = flickrService.getSearch(apiKey, term)
                callback.onResponse(apiResponse.photos.photo.map(adapter::convert))
            }
            catch (e: Exception){
                callback.onFailure(e)
            }
        }
    }
}