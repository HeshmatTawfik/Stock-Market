package com.heshmat.data.mapper

import com.heshmat.data.remote.dto.company_info.IntradayInfoDto
import com.heshmat.domain.model.company_info.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun IntradayInfoDto.toIntradayInfo(): IntradayInfo{
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timestamp,formatter)
    return IntradayInfo(
        date = localDateTime,
        close = close
    )

}