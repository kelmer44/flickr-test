package net.kelmer.android.data.repository

import net.kelmer.android.data.service.FlickrService
import net.kelmer.android.domain.Photo
import net.kelmer.android.domain.PhotoAdapter
import net.kelmer.android.network.Task

class PhotoRepositoryImpl(
    private val flickrService: FlickrService,
    private val apiKey: String
) : PhotoRepository {

    private val adapter = PhotoAdapter()
    override fun search(term: String): Task<List<Photo>> {
        return object : Task<List<Photo>> {
            override fun call(): List<Photo> {
                val apiResponse = flickrService.getSearch(apiKey, term)
                return apiResponse.photos.photo.map(adapter::convert)
            }
        }
    }
}