package com.example.searchresources.ui.util

import android.content.res.Resources
import android.util.Log
import com.example.searchresources.data.model.SearchImageResponse
import com.example.searchresources.domain.model.toEntity
import com.example.searchresources.ui.searchList.SearchListItem
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun convertItems(data : SearchImageResponse): List<SearchListItem> {
    val documents = data.toEntity().documents ?: return emptyList()

    return documents.map {
        SearchListItem(
            thumbnail = it.thumbnail_url,
            site = it.display_sitename,
            datetime = convertDatetime(it.datetime!!),
        )
    }!!
}

private fun convertDatetime(datetime: String): String {
    // 원래 문자열을 ZonedDateTime으로 파싱
    val zonedDateTime = ZonedDateTime.parse(datetime)

    // LocalDateTime으로 변환
    val localDateTime = zonedDateTime.toLocalDateTime()

    // 원하는 출력 포맷 정의
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    // 포맷 적용 후 문자열로 변환
    val formattedString = localDateTime.format(formatter)

    // 결과 출력
    // Log.d("convert", formattedString)

    return formattedString
}

fun Float.fromDpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()