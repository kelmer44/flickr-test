package net.kelmer.android.util

import android.app.Application
import android.content.Context
import net.kelmer.android.data.repository.PhotoRepository
import net.kelmer.android.data.repository.PhotoRepositoryImpl
import net.kelmer.android.data.service.FlickrService
import net.kelmer.android.data.service.FlickrServiceImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

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
    fun retrofit(): Retrofit
    fun httpClient(): OkHttpClient
    fun apiKey(): String
}

open class DefaultServiceLocator(val app: Application) : ServiceLocator {


    override fun apiKey() = "12f7e02a64502c622306c2a9145997a6"

    override fun getRepository() : PhotoRepository {
        return PhotoRepositoryImpl(flickrService(), apiKey())
    }

    override fun flickrService() : FlickrService {
        return retrofit().create(FlickrService::class.java)
    }

    override fun retrofit() : Retrofit = Retrofit.Builder()
        .baseUrl("https://api.flickr.com")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient())
        .build()

    override fun httpClient() : OkHttpClient = OkHttpClient.Builder().addInterceptor(
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build()




}