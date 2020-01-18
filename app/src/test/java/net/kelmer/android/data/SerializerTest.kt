package net.kelmer.android.data

import org.junit.Test

import org.junit.Assert.*

class SerializerTest {

    val serializer: Serializer = GsonSerializer()


    @Test
    fun `When provided a valid json, serializer returns a valid object`() {

    }


    @Test
    fun `When provided an invalid json, serializer throws an Exception`() {
    }

}