package net.kelmer.android.domain

data class PhotoListPage(val term: String,
                         val page: Int,
                         val items: List<Photo>) {
    fun isFirstPage(): Boolean {
        return page == 0
    }
}