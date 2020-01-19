package net.kelmer.android.data.repository

import net.kelmer.android.domain.Photo
import net.kelmer.android.network.Callback
import net.kelmer.android.network.DeferredTaskRunner
import net.kelmer.android.network.Task

interface PhotoRepository {

    fun search(term: String): DeferredTaskRunner<List<Photo>>
}