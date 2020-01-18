package net.kelmer.android.data

import android.util.Log
import net.kelmer.android.data.model.ApiResponse
import net.kelmer.android.data.model.PhotoEntity
import net.kelmer.android.data.model.PhotoListResponse
import net.kelmer.android.util.ServiceLocator
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class SerializerTest {

    private val serializer = ServiceLocator.instance().serializer()

    private val json: String =
        "{\"photos\":{\"page\":1,\"pages\":92293,\"perpage\":2,\"total\":\"184585\",\"photo\":[{\"id\":\"49402615537\",\"owner\":\"8740272@N04\",\"secret\":\"e186b7922e\",\"server\":\"65535\",\"farm\":66,\"title\":\"Having Fun In a Warm Winter (to be continued)\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0},{\"id\":\"49401885523\",\"owner\":\"143537090@N02\",\"secret\":\"53f5fa566b\",\"server\":\"65535\",\"farm\":66,\"title\":\"Mackerel Tabby Cat\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0}]},\"stat\":\"ok\"}"


    @Before
    fun setUp() {


    }


    @Test
    fun `When provided a valid json, serializer returns a valid object`() {
        val result = ApiResponse(
            PhotoListResponse(
                page = 1,
                pages = 92293,
                perPage = 2,
                total = 184585,
                photo = listOf(
                    PhotoEntity(
                        id = "49402615537",
                        owner = "8740272@N04",
                        secret = "e186b7922e",
                        server = 65535,
                        farm = 66,
                        title = "Having Fun In a Warm Winter (to be continued)"
                    ),
                    PhotoEntity(
                        id = "49401885523",
                        owner = "143537090@N02",
                        secret = "53f5fa566b",
                        server = 65535,
                        farm = 66,
                        title = "Mackerel Tabby Cat"
                    )
                )
            ),
            "ok"
        )
        val deserialized = serializer.deserialize(json)

        assertEquals(deserialized, result)
    }


    @Test(expected = Throwable::class)
    fun `When provided an invalid json, serializer throws an Exception`() {
        val json = "{ \"test\":\"this is a test json\"}"
        val deserializer = serializer.deserialize(json)
    }

}