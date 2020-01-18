package net.kelmer.android.data.repository

import io.reactivex.Single
import net.kelmer.android.data.service.FlickrService
import net.kelmer.android.domain.Photo
import net.kelmer.android.domain.PhotoAdapter

class PhotoRepositoryImpl(private val service: FlickrService, private val apiKey: String) : PhotoRepository {

    private val adapter = PhotoAdapter()
    override fun search(term: String): Single<List<Photo>> {
       return service.getSearch(apiKey, term)
           .map {
               it.photos.photo.map(adapter::convert)
           }
    }
}