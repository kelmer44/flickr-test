package net.kelmer.android.data.serializer

import com.google.gson.Gson
import net.kelmer.android.data.model.ApiResponse

class GsonSerializer(private val gson: Gson): Serializer {

    override fun deserialize(json: String): ApiResponse {
        return gson.fromJson<ApiResponse>(json, ApiResponse::class.java)
    }
}