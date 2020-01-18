package net.kelmer.android.util

import android.app.Application
import android.content.Context
import com.google.gson.GsonBuilder
import net.kelmer.android.data.GsonSerializer
import net.kelmer.android.data.Serializer
import net.kelmer.android.data.repository.PhotoRepository
import net.kelmer.android.data.repository.PhotoRepositoryImpl
import net.kelmer.android.data.service.FlickrService
import net.kelmer.android.data.service.FlickrServiceImpl
import net.kelmer.android.utils.HttpClient
import net.kelmer.android.utils.StringResponseHttpClient

interface ServiceLocator {


    companion object {

        private val LOCK = Any()
        private var instance: ServiceLocator? = null
        fun instance(context: Context): ServiceLocator {
            synchronized(LOCK) {
                if (instance == null) {
                    instance = DefaultServiceLocator(
                        app = context.applicationContext as Application)
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

open class DefaultServiceLocator(val app: Application) : ServiceLocator {


    override fun baseUrl() = "https://api.flickr.com"
    override fun apiKey() = "12f7e02a64502c622306c2a9145997a6"

    override fun getRepository() : PhotoRepository {
        return PhotoRepositoryImpl(flickrService(), apiKey())
    }

    override fun flickrService() : FlickrService {
        return FlickrServiceImpl(baseUrl(), serializer(), client())
    }

    override fun serializer() : Serializer {
        return GsonSerializer()
    }
    override fun client(): HttpClient {
        return  StringResponseHttpClient()
    }



}