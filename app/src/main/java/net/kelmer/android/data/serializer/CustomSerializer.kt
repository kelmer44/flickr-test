package net.kelmer.android.data.serializer

import net.kelmer.android.data.model.ApiResponse
import net.kelmer.android.data.model.PhotoEntity
import net.kelmer.android.data.model.PhotoListResponse
import org.json.JSONArray
import org.json.JSONObject





class CustomSerializer : Serializer {


    override fun deserialize(json: String): ApiResponse {
        val root = JSONObject(json)

        val stat = root.getString("stat")

        val photos = root.getJSONObject("photos")

        val page = photos.getInt("page")
        val pages = photos.getInt("pages")
        val perPage = photos.getInt("perpage")
        val total = photos.getInt("total")
        val photo = photos.getJSONArray("photo")

        val actualPhotos  = mutableListOf<PhotoEntity>()
        for(i in 0 until photo.length()){
            val thisPhoto = photo.getJSONObject(i)
            val id = thisPhoto.getString("id")
            val owner = thisPhoto.getString("owner")
            val title = thisPhoto.getString("title")
            val secret = thisPhoto.getString("secret")
            val server = thisPhoto.getInt("server")
            val farm = thisPhoto.getInt("farm")
            actualPhotos.add(PhotoEntity(id,owner, title, secret, server, farm))
        }

        return ApiResponse(PhotoListResponse(page,pages,perPage, total, photo = actualPhotos),stat)
    }
}