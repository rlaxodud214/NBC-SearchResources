package com.example.searchresources.domain.model

import com.example.searchresources.data.model.ImageDocumentResponse
import com.example.searchresources.data.model.MetaResponse
import com.example.searchresources.data.model.SearchImageResponse

fun SearchImageResponse.toEntity() = SearchImageEntity(
    meta = meta?.toEntity(),
    documents = documents?.map {
        it.toEntity()
    }
)

fun MetaResponse.toEntity() = MetaEntity(
    totalCount = totalCount,
    pageableCount = pageableCount,
    isEnd = isEnd,
)

fun ImageDocumentResponse.toEntity() = ImageDocumentEntity(
    collection = collection,
    thumbnail_url = thumbnail_url,
    image_url = image_url,
    width = width,
    height = height,
    display_sitename = display_sitename,
    doc_url = doc_url,
    datetime = datetime,
)