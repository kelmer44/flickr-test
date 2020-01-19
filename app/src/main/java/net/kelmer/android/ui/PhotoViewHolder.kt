package net.kelmer.android.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_photo.view.*
import net.kelmer.android.domain.Photo

class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(photo: Photo) = with(itemView){
        Glide.with(itemView)
            .load(photo.url)
            .into(item_photo_image)

//        ImageFetcher(itemView.context)
//            .load(photo.url, item_photo_image, R.drawable.ic_search_white)
        item_photo_title.text = photo.title
    }
}