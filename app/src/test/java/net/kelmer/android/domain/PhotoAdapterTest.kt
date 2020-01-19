package net.kelmer.android.domain

import net.kelmer.android.data.model.PhotoEntity
import org.junit.Assert.*
import org.junit.Test

class PhotoAdapterTest {


    @Test
    fun `When receiving a PhotoEntity, the adapter returns a Photo object with an assembled URL`() {

        val adapter = PhotoAdapter()
        val entity = PhotoEntity(
            id = ENTITY_ID,
            owner = "8740272@N04",
            secret = SECRET,
            server = SERVER,
            farm = FARM,
            title = "Having Fun In a Warm Winter (to be continued)"
        )
        val photo = adapter.convert(entity)

        val expectedURL = "https://farm$FARM.staticflickr.com/$SERVER/${ENTITY_ID}_$SECRET.jpg"
        assertEquals(photo.id, ENTITY_ID)
        assertEquals(photo.url, expectedURL)

    }

    companion object {
        const val ENTITY_ID = "49402615537"
        const val SERVER = 65535
        const val FARM = 66
        const val SECRET = "e186b7922e"
    }
}