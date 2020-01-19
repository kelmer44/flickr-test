package net.kelmer.android.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.kelmer.android.domain.Photo
import net.kelmer.android.flickrsearch.R

class PhotoListAdapter : RecyclerView.Adapter<PhotoViewHolder>() {

    private var photoList: List<Photo> = emptyList()

    fun updateList(list: List<Photo>) {
        photoList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(itemView = itemView)
    }

    override fun getItemCount(): Int = photoList.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photoList[position])
    }

    fun append(items: List<Photo>) {
        photoList = photoList.plus(items)
        notifyDataSetChanged()
    }
}
