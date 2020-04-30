package net.kelmer.android.util

import net.kelmer.android.data.serializer.Serializer
import net.kelmer.android.data.repository.PhotoRepository
import net.kelmer.android.data.repository.PhotoRepositoryImpl
import net.kelmer.android.data.serializer.CustomSerializer
import net.kelmer.android.data.service.FlickrService
import net.kelmer.android.data.service.FlickrServiceImpl
import net.kelmer.android.utils.HttpClient
import net.kelmer.android.utils.StringResponseHttpClient

interface ServiceLocator {


    companion object {

        private val LOCK = Any()
        private var instance: ServiceLocator? = null
        fun instance(): ServiceLocator {
            synchronized(LOCK) {
                if (instance == null) {
                    instance = DefaultServiceLocator()
                }
                return instance!!
            }
        }
    }

    fun getRepository(): PhotoRepository
    fun flickrService(): FlickrService
    fun apiKey(): String
    fun baseUrl(): String
    fun serializer(): Serializer
    fun client(): HttpClient
}

open class DefaultServiceLocator : ServiceLocator {


    override fun baseUrl() = "https://api.flickr.com"
    override fun apiKey() = ""

    override fun getRepository() : PhotoRepository {
        return PhotoRepositoryImpl(flickrService(), apiKey())
    }

    override fun flickrService() : FlickrService {
        return FlickrServiceImpl(baseUrl(), serializer(), client())
    }

    override fun serializer() : Serializer {
        return CustomSerializer()
    }


    override fun client(): HttpClient {
        return  StringResponseHttpClient()
    }



}