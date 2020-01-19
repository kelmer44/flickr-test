package net.kelmer.android.common

import android.content.Context
import net.kelmer.android.data.repository.PhotoRepository
import net.kelmer.android.data.repository.PhotoRepositoryImpl
import net.kelmer.android.data.serializer.CustomSerializer
import net.kelmer.android.data.serializer.Serializer
import net.kelmer.android.data.service.FlickrService
import net.kelmer.android.data.service.FlickrServiceImpl
import net.kelmer.android.flickrsearch.R
import net.kelmer.android.network.client.HttpClient
import net.kelmer.android.network.client.StringResponseHttpClient

interface ServiceLocator {

    companion object {

        private val LOCK = Any()
        private var instance: ServiceLocator? = null
        fun instance(context: Context): ServiceLocator {
            synchronized(LOCK) {
                if (instance == null) {
                    instance =
                        DefaultServiceLocator(context)
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

class DefaultServiceLocator(val context: Context) : ServiceLocator {

    override fun baseUrl() = context.getString(R.string.base_url)
    override fun apiKey() = context.getString(R.string.api_key)

    override fun getRepository(): PhotoRepository {
        return PhotoRepositoryImpl(flickrService(), apiKey())
    }

    override fun flickrService(): FlickrService {
        return FlickrServiceImpl(baseUrl(), serializer(), client())
    }

    override fun serializer(): Serializer {
        return CustomSerializer()
    }

    override fun client(): HttpClient {
        return StringResponseHttpClient()
    }
}
