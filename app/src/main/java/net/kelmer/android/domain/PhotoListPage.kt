package net.kelmer.android.domain

data class PhotoListPage(val term: String,
                         val page: Int,
                         val items: List<Photo>,
                         val hasNextPage: Boolean) {
    //Flickr calls 1 the first page
    fun isFirstPage(): Boolean {
        return page == 1
    }
}