package net.kelmer.android.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import net.kelmer.android.domain.Photo
import net.kelmer.android.flickrsearch.R

class PhotoListAdapter : RecyclerView.Adapter<PhotoViewHolder>() {

    private var photoList: MutableList<Photo> = mutableListOf()

    fun updateList(list: List<Photo>) {
        val diffCallback = DiffCallback(photoList, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        photoList.clear()
        photoList.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    fun append(items: List<Photo>) {
        val newList = photoList.plus(items)
        val diffCallback = DiffCallback(photoList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        photoList.clear()
        photoList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)


    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(itemView = itemView)
    }

    override fun getItemCount(): Int = photoList.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photoList[position])
    }

    class DiffCallback(private val oldList: List<Photo>, private val newList: List<Photo>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id === newList.get(newItemPosition).id
        }

        override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            val (_, _, name) = oldList[oldPosition]
            val (_, _, name1) = newList[newPosition]

            return name == name1
        }

        override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
            return super.getChangePayload(oldPosition, newPosition)
        }
    }


}
