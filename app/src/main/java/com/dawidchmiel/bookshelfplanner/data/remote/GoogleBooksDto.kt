package com.dawidchmiel.bookshelfplanner.data.remote

import com.dawidchmiel.bookshelfplanner.model.Book
import com.squareup.moshi.Json

data class GoogleBooksResponse(
    val items: List<VolumeDto> = emptyList()
)

data class VolumeDto(
    val id: String,
    val volumeInfo: VolumeInfoDto?
)

data class VolumeInfoDto(
    val title: String?,
    val authors: List<String>?,
    val description: String?,
    val publishedDate: String?,
    val pageCount: Int?,
    val imageLinks: ImageLinksDto?
)

data class ImageLinksDto(
    @Json(name = "thumbnail") val thumbnail: String?,
    @Json(name = "smallThumbnail") val smallThumbnail: String?
)

fun VolumeDto.toBook(): Book {
    val info = volumeInfo
    val rawImage = info?.imageLinks?.thumbnail ?: info?.imageLinks?.smallThumbnail
    return Book(
        id = id,
        title = info?.title ?: "Untitled book",
        authors = info?.authors?.joinToString() ?: "Unknown author",
        description = info?.description ?: "No description available.",
        thumbnailUrl = rawImage?.replace("http://", "https://"),
        publishedDate = info?.publishedDate,
        pageCount = info?.pageCount
    )
}
