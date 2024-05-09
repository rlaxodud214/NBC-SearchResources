package com.example.searchresources.domain.model

data class SearchImageEntity(
    val meta: MetaEntity?,
    var documents: List<ImageDocumentEntity>?,
)

data class MetaEntity(
    val totalCount: Int?,
    val pageableCount: Int?,
    val isEnd: Boolean?,
)

data class ImageDocumentEntity(
    val collection: String?,
    val datetime: String?,
    val display_sitename: String?,
    val doc_url: String?,
    val height: Int?,
    val width: Int?,
    val image_url: String?,
    val thumbnail_url: String?,
)
