package com.heshmat.data.csv

import com.heshmat.data.mapper.toIntradayInfo
import com.heshmat.data.remote.dto.company_info.IntradayInfoDto
import com.heshmat.domain.model.company_info.IntradayInfo
import com.heshmat.domain.model.company_info.filterIntraDayList
import com.heshmat.domain.model.company_info.sortIntradayInfoList
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.DayOfWeek
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntradayInfoParser @Inject constructor() : CSVParser<IntradayInfo> {
    companion object {
        private const val timeStampIndex = 0
        private const val closeInfoIndex = 4
    }

    override suspend fun parse(steam: InputStream): List<IntradayInfo> {
        val csvReader = CSVReader(InputStreamReader(steam))
        return withContext(Dispatchers.IO) {
            csvReader.readAll().drop(1).mapNotNull { line ->

                val timeStamp = line.getOrNull(timeStampIndex) ?: return@mapNotNull null
                val closeInfo = line.getOrNull(closeInfoIndex) ?: return@mapNotNull null
                val dto = IntradayInfoDto(timeStamp, closeInfo.toDouble())
                dto.toIntradayInfo()
            }.filterIntraDayList {
                val lastOpenedDay = when (LocalDateTime.now().dayOfWeek){
                    DayOfWeek.MONDAY -> 3
                    DayOfWeek.SUNDAY -> 2
                    else -> 1
                }

                it.date.dayOfMonth == LocalDateTime.now().minusDays(lastOpenedDay.toLong()).dayOfMonth
            }.sortIntradayInfoList {
                it.date.hour
            }.also {
                csvReader.close()
            }
        }
    }
}

