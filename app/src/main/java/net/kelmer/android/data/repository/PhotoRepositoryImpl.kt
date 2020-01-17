package net.kelmer.android.data.repository

import io.reactivex.Single
import net.kelmer.android.data.model.PhotoEntity
import net.kelmer.android.data.service.FlickrService
import net.kelmer.android.domain.Photo
import net.kelmer.android.domain.PhotoAdapter

class PhotoRepositoryImpl(val service: FlickrService) : PhotoRepository {

    private val adapter = PhotoAdapter()
    private val apiKey = "12f7e02a64502c622306c2a9145997a6"
    override fun search(term: String): Single<List<Photo>> {
       return service.getSearch(apiKey, term)
           .map {
               it.photos.photo.map(adapter::convert)
           }
    }
}