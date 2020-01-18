package net.kelmer.android.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.kelmer.android.common.Resource
import net.kelmer.android.data.repository.PhotoRepository
import net.kelmer.android.domain.Photo
import net.kelmer.android.network.Callback

class MainViewModel(private val photoRepository: PhotoRepository) : ViewModel() {



    val photoLiveData: MutableLiveData<Resource<List<Photo>>> = MutableLiveData()
    fun search(term: String) {
        photoLiveData.value = Resource.inProgress()
        photoRepository.search(term, object: Callback<List<Photo>>{
            override fun onResponse(data: List<Photo>) {
                photoLiveData.postValue(Resource.success(data))
            }

            override fun onFailure(t: Throwable) {
                photoLiveData.postValue(Resource.failure(t))
            }

        })
    }

}