package net.kelmer.android.domain

import net.kelmer.android.common.Adapter
import net.kelmer.android.data.model.PhotoEntity

class PhotoAdapter : Adapter<PhotoEntity, Photo> {
    override fun convert(input: PhotoEntity): Photo {

        val url  = "https://farm${input.farm}.staticflickr.com/${input.server}/${input.id}_${input.secret}.jpg"
        return Photo(input.id,
            input.owner,
            input.title,
            url)
    }


}