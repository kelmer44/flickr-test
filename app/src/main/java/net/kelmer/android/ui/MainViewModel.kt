package net.kelmer.android.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import net.kelmer.android.common.Resource
import net.kelmer.android.data.model.PhotoEntity
import net.kelmer.android.data.repository.PhotoRepository
import net.kelmer.android.data.repository.PhotoRepositoryImpl
import net.kelmer.android.data.service.FlickrService
import net.kelmer.android.domain.Photo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

    private val client = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build()
    private val service: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.flickr.com")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
    private val photoRepository: PhotoRepository = PhotoRepositoryImpl(service.create(FlickrService::class.java))

    private val disposables = CompositeDisposable()


    val photoLiveData: MutableLiveData<Resource<List<Photo>>> = MutableLiveData()
    fun search(term: String) {

        photoRepository.search(term)
            .toFlowable()
            .map {
                Resource.success(it)
            }
            .onErrorReturn {
                Resource.failure(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .startWith(Resource.inProgress())
            .subscribeBy(
                onNext = {
                    photoLiveData.value = it
                })
            .addTo(disposables)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}