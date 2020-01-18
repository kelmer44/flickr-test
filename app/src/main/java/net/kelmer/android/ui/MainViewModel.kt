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
import net.kelmer.android.data.service.FlickrServiceImpl
import net.kelmer.android.domain.Photo

class MainViewModel : ViewModel() {

    private val service : FlickrService = FlickrServiceImpl("https://api.flickr.com")
    private val photoRepository: PhotoRepository = PhotoRepositoryImpl(service)

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