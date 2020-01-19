package net.kelmer.android.data.repository

import net.kelmer.android.data.service.FlickrService
import net.kelmer.android.domain.PhotoAdapter
import net.kelmer.android.domain.PhotoListPage
import net.kelmer.android.network.task.TaskRunner
import net.kelmer.android.network.task.Task
import net.kelmer.android.network.task.TaskRunnerImpl

class PhotoRepositoryImpl(
    private val flickrService: FlickrService,
    private val apiKey: String
) : PhotoRepository {
    companion object {
        const val PERPAGE = 20
    }

    private val adapter = PhotoAdapter()

    override fun search(term: String, page: Int): TaskRunner<PhotoListPage> {
        return TaskRunnerImpl(object :
            Task<PhotoListPage> {
            override fun call(): PhotoListPage {
                val apiResponse = flickrService.search(apiKey, term, PERPAGE, page)
                return PhotoListPage(term, page, apiResponse.photos.photo.map(adapter::convert),page < apiResponse.photos.pages)
            }
        })
    }
}