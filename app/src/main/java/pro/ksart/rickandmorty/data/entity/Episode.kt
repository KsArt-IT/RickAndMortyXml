package pro.ksart.rickandmorty.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Episode(
    val id: Int,
    val name: String,
    @Json(name = "air_date")
    val airDate: String,
    val episode: String,
    val url: String,
    val created: String,
)
