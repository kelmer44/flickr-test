package net.kelmer.android.data.model

/**
 * {
"id": "49397292978",
"owner": "8740272@N04",
"secret": "4922ab576e",
"server": "65535",
"farm": 66,
"title": "Happy Black Friday ;-)",
"ispublic": 1,
"isfriend": 0,
"isfamily": 0
}

 */
data class PhotoEntity(
    val id: String,
    val owner: String,
    val title: String,
    val secret: String,
    val server: Int,
    val farm: Int
) {
}