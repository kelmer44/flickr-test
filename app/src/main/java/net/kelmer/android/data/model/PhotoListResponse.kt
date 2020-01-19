package net.kelmer.android.data.model

data class PhotoListResponse(
    val page: Int,
    val pages: Int,
    val perPage: Int,
    val total: Int,
    val photo: List<PhotoEntity>
)
