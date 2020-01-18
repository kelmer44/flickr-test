package net.kelmer.android.data.serializer

import net.kelmer.android.data.model.ApiResponse

interface Serializer {

    fun deserialize(json: String) : ApiResponse
}