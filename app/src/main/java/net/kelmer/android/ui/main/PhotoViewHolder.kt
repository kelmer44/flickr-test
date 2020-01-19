package net.kelmer.android.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_photo.view.*
import net.kelmer.android.domain.Photo
import net.kelmer.android.flickrsearch.R
import net.kelmer.android.network.imagefetcher.ImageFetcher

class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(photo: Photo) = with(itemView) {

        val imageHeight = itemView.context.resources.getDimensionPixelSize(R.dimen.item_photo_height)
        ImageFetcher(itemView.context)
            .load(photo.url,
                item_photo_image,
                R.drawable.ic_search_white,
                imageHeight)
        item_photo_title.text = photo.title
    }
}
