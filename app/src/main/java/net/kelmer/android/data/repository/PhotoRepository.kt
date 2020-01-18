package net.kelmer.android.data.repository

import net.kelmer.android.domain.Photo
import net.kelmer.android.network.Callback

interface PhotoRepository {

    fun search(term: String, callback: Callback<List<Photo>>)
}