package polis.app.movies.data.model

import com.squareup.moshi.Json

data class FavoriteRequestBody(
    @field:Json(name = "favorite")
    val favorite: Boolean,
    @field:Json(name = "media_id")
    val mediaId: Int,
    @field:Json(name = "media_type")
    val mediaType: String = "movie"
)