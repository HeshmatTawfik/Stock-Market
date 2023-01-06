package com.heshmat.stockmarket.company_info

import com.heshmat.domain.model.company_info.CompanyInfo
import com.heshmat.domain.model.company_info.IntradayInfo

data class CompanyInfoState(
    val intradayInfoList:List<IntradayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error:String? = null
)
