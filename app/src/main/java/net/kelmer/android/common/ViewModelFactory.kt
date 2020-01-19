package net.kelmer.android.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.kelmer.android.data.repository.PhotoRepository
import net.kelmer.android.ui.main.MainViewModel

class ViewModelFactory(private val repository: PhotoRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}
