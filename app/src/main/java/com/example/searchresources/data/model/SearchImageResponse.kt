package com.example.searchresources.data.model

import com.google.gson.annotations.SerializedName

data class SearchImageResponse(
    @SerializedName("metaResponse") val meta: MetaResponse,
    @SerializedName("documents") var documents: List<ImageDocumentResponse>,
)

data class ImageDocumentResponse(
    @SerializedName("collection") val collection: String,
    @SerializedName("datetime") val datetime: String,
    @SerializedName("display_sitename") val display_sitename: String,
    @SerializedName("doc_url") val doc_url: String,
    @SerializedName("height") val height: Int,
    @SerializedName("width") val width: Int,
    @SerializedName("image_url") val image_url: String,
    @SerializedName("thumbnail_url") val thumbnail_url: String,
)

data class MetaResponse(
    @SerializedName("totalCount") val totalCount: Int,
    @SerializedName("pageableCount") val pageableCount: Int,
    @SerializedName("isEnd") val isEnd: Boolean,
)