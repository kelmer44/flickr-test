package net.kelmer.android.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.kelmer.android.common.Resource
import net.kelmer.android.data.repository.PhotoRepository
import net.kelmer.android.domain.PhotoListPage
import net.kelmer.android.network.task.Callback
import net.kelmer.android.network.task.FutureTask

class MainViewModel(private val photoRepository: PhotoRepository) : ViewModel() {

    val photoLiveData: MutableLiveData<Resource<PhotoListPage>> = MutableLiveData()

    private var lastTask: FutureTask<*>? = null
    var lastPage: PhotoListPage? = null

    fun search(term: String, page: Int = 1) {
        // Cancel if there was a previous request
        lastTask?.cancel()

        photoLiveData.value = Resource.inProgress()
        lastTask = photoRepository.search(term, page).execute(object :
            Callback<PhotoListPage> {
            override fun onResponse(data: PhotoListPage) {
                lastPage = data
                photoLiveData.value = Resource.success(data)
            }

            override fun onFailure(t: Throwable) {
                photoLiveData.value = Resource.failure(t)
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        lastTask?.cancel()
    }

    fun loadMore() {
        lastPage?.run {
            if (hasNextPage) {
                search(this.term, this.page + 1)
            }
        }
    }
}
