package net.kelmer.android.data.repository

import net.kelmer.android.domain.PhotoListPage
import net.kelmer.android.network.task.TaskRunner

interface PhotoRepository {

    fun search(term: String, page: Int = 0): TaskRunner<PhotoListPage>
}
