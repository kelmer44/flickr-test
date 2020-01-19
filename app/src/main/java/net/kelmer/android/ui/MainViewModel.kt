package net.kelmer.android.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.kelmer.android.common.Resource
import net.kelmer.android.data.repository.PhotoRepository
import net.kelmer.android.domain.Photo
import net.kelmer.android.network.Callback
import net.kelmer.android.network.TaskRunner
import java.util.concurrent.Future

class MainViewModel(private val photoRepository: PhotoRepository) : ViewModel() {

    private val taskRunner = TaskRunner<List<Photo>>()

    val photoLiveData: MutableLiveData<Resource<List<Photo>>> = MutableLiveData()
    private var lastTask : Future<*>? = null
    fun search(term: String) {
        //Cancel if there was a previous request
        lastTask?.cancel(true)
        photoLiveData.value = Resource.inProgress()
        lastTask = taskRunner.execute(photoRepository.search(term), object: Callback<List<Photo>>{
            override fun onResponse(data: List<Photo>) {
                photoLiveData.value = Resource.success(data)
            }

            override fun onFailure(t: Throwable) {
                photoLiveData.value = Resource.failure(t)
            }

        })
    }

    override fun onCleared() {
        super.onCleared()
        taskRunner.cancel()
    }
}