package com.heshmat.domain.model.company_info

import java.time.LocalDateTime

data class IntradayInfo(
    val date: LocalDateTime,
    val close: Double
)

fun List<IntradayInfo>.filterIntraDayList(filterCriteria:(IntradayInfo)->Boolean):List<IntradayInfo>{
  return this.filter {
       filterCriteria(it)
   }
}

fun List<IntradayInfo>.sortIntradayInfoList(sortCriteria:(IntradayInfo)->Int):List<IntradayInfo>{
    return this.sortedBy {
        sortCriteria(it)
    }
}
