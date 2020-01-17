package net.kelmer.android.data.repository

import io.reactivex.Single
import net.kelmer.android.data.model.PhotoEntity
import net.kelmer.android.domain.Photo

interface PhotoRepository {

    fun search(term: String) : Single<List<Photo>>
}