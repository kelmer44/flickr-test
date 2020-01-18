package net.kelmer.android.data

import com.google.gson.GsonBuilder
import net.kelmer.android.data.model.ApiResponse

class GsonSerializer  : Serializer{

    val gson = GsonBuilder().create()

    override fun deserialize(json: String): ApiResponse? {
        return gson.fromJson<ApiResponse>(json, ApiResponse::class.java)
    }
}